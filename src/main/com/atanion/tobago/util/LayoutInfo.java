/*
  * Copyright (c) 2002 Atanion GmbH, Germany
  * All rights reserved. Created 10.11.2003 at 13:23:46.
  * $Id$
  */
package com.atanion.tobago.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class LayoutInfo{

  private static final int FREE = -1;
  private static final Log log = LogFactory.getLog(LayoutInfo.class);
  public static final int HIDE = -2;
  public static final String HIDE_CELL = "hide";




  private int cellsLeft;
  private int spaceLeft;
  private int[] spaces;
  private String[] layoutTokens;

  public LayoutInfo(int cellCount, int space, String layout) {
    this(cellCount, space, createLayoutTokens(layout));
  }

  public LayoutInfo(int cellCount, int space, String[] layoutTokens) {

    this.layoutTokens = layoutTokens;
    if (layoutTokens.length == cellCount) {
      this.cellsLeft = cellCount;
      this.spaceLeft = space;
      createAndInitSpaces(cellCount, FREE);
    } else if (layoutTokens.length > 0) {
      if (log.isWarnEnabled()) {
        StringBuffer layout = new StringBuffer();
        for (int i = 0; i < layoutTokens.length; i++) {
          if (layout.length() > 0) {
            layout.append(";");
          }
          layout.append(layoutTokens[i]);
        }
        log.warn("layoutTokens.length != cellCount : "
            + layoutTokens.length + " != " + cellCount + ". " +
                "layout='" + layout.toString() + "'");
      }
      layoutTokens = new String[0];
    }
    if (layoutTokens.length == 0) {
      if (cellCount == 0) {
        log.warn("cellCount == 0 : using 1 to calulate space");
        cellCount = 1;
      }
      createAndInitSpaces(cellCount, space / cellCount);
    }
  }

  private void createAndInitSpaces(int columns, int initValue) {
    spaces = new int[columns];
    for (int j = 0; j < spaces.length; j++) {
      spaces[j] = initValue;
    }
  }

  public void update(int space, int index){
    spaceLeft -= space;
    cellsLeft--;
    if (index < spaces.length) {
      spaces[index] = space;
      if (spaceLeft < 0) {
        if (log.isWarnEnabled()) {
          log.warn("More space used than available! set left to 0!");
        }
        spaceLeft = 0;
      }
      if (spaceLeft <1 && columnsLeft()) {
        if (log.isWarnEnabled()) {
          log.warn("There are columns left but no more Space!");
        }
      }
    }
    else {
      log.warn("More Space to assign (" + space + "px) but no more column's!"
          + " More layoutTokens than column tag's?");
    }
  }

  public boolean columnsLeft(){
    return cellsLeft > 0;
  }


  public void handleIllegalTokens() {
    for (int i = 0 ; i<spaces.length; i++) {
      if (isFree(i)) {
        if (log.isWarnEnabled()) {
          log.warn("Illegal layout token pattern \"" + layoutTokens[i]
              + "\" ignored, set to 0px !");
        }
        spaces[i] = 0;
      }
    }
  }


  public static String[] createLayoutTokens(String columnLayout) {
    Vector tokens = new Vector();
    if (columnLayout != null) {
      StringTokenizer tokenizer = new StringTokenizer(columnLayout, ";");
      while (tokenizer.hasMoreTokens()) {
        tokens.add(tokenizer.nextToken().trim());
      }
    }
    return (String[]) tokens.toArray(new String[tokens.size()] );
  }

  public boolean isFree(int column) {
    return spaces[column] == FREE;
  }

  public int getSpaceForColumn(int column) {
    return spaces[column];
  }

  public int getSpaceLeft() {
    return spaceLeft;
  }

  public String[] getLayoutTokens() {
    return layoutTokens;
  }

  public boolean hasLayoutTokens() {
    return layoutTokens.length > 0;
  }

  public List getSpaceList() {
    Vector v = new Vector(spaces.length);
    for (int i = 0; i < spaces.length; i++) {
      v.add(new Integer(spaces[i]));
    }
    return v;
  }

  public void handleSpaceLeft() {
    if (spaceLeft > 0) {
      if (log.isDebugEnabled()) {
        log.debug("spread spaceLeft (" + spaceLeft + "px) to columns");
        log.debug("spaces before spread :" + arrayAsString(spaces));
      }

      for (int i = 0; i < layoutTokens.length; i++) {
        if ("*".equals(layoutTokens[i]) ) {
          addSpace(spaceLeft, i);
          break;
        }
      }
      boolean found = false;
      while (spaceLeft > 0) {
//        for (int i = 0; i < layoutTokens.length; i++) {
        for (int i = layoutTokens.length - 1; i > -1; i--) {
          String layoutToken = layoutTokens[i];
          if (spaceLeft > 0 && layoutToken.matches("^\\d+\\*")) {
            found = true;
            addSpace(1, i);
          }
        }
        if (! found) {
          break;
        }
      }
    }
    if (spaceLeft > 0 && log.isWarnEnabled()) {
      log.warn("Space left after spreading : " + spaceLeft + "px!");
    }
    if (log.isDebugEnabled()) {
      log.debug("spaces after spread  :" + arrayAsString(spaces));
    }
  }

  private String arrayAsString(int[] spaces) {
    StringBuffer sb = new StringBuffer("[");
    for (int i = 0; i < spaces.length; i++) {
      sb.append(spaces[i]);
      sb.append(", ");
    }
    sb.replace(sb.lastIndexOf(", "), sb.length(), "]");
    return sb.toString();
  }

  private void addSpace(int space, int i) {
    spaces[i] += space;
    spaceLeft -= space;
  }







  public void parseHides(int padding) {
    String[] tokens = getLayoutTokens();
    for (int i = 0; i < tokens.length; i++) {
      if (tokens[i].equals(HIDE_CELL)) {
          update(0, i);
          spaces[i] = HIDE;
        if (i != 0) {
          spaceLeft += padding;
        }
        if (log.isDebugEnabled()) {
            log.debug("set column " + i + " from " + tokens[i]
                + " to hide " );
          }
      }
    }
  }

  public void parsePixels() {
    String[] tokens = getLayoutTokens();
    for (int i = 0; i < tokens.length; i++) {
      if (tokens[i].endsWith("px")) {
        String token = tokens[i].substring(0, tokens[i].length() - 2);
        try {
          int w = Integer.parseInt(token);
          update(w, i);
          if (log.isDebugEnabled()) {
            log.debug("set column " + i + " from " + tokens[i]
                + " to with " + w);
          }
        } catch (NumberFormatException e) {
          if (log.isWarnEnabled()) {
            log.warn("NumberFormatException parsing " + tokens[i]);
          }
        }
      }
    }
  }


  public void parsePercent(double innerWidth) {
    String[] tokens = getLayoutTokens();
    if (columnsLeft()) {
      for (int i = 0; i < tokens.length; i++) {
        if (isFree(i) && tokens[i].endsWith("%")) {
          String token = tokens[i].substring(0, tokens[i].length() - 1);
          try {
            int percent = Integer.parseInt(token);
            int w = (int) (innerWidth / 100 * percent);
            update(w, i);
            if (log.isDebugEnabled()) {
              log.debug("set column " + i + " from " + tokens[i]
                  + " to with " + w);
            }
          } catch (NumberFormatException e) {
            if (log.isWarnEnabled()) {
              log.warn("NumberFormatException parsing " + tokens[i]);
            }
          }
        }
      }
    }
  }

  public void parsePortions() {
    String[] tokens = getLayoutTokens();
    if (columnsLeft()) {
      //   1. count portions
      int portions = 0;
      for (int i = 0; i < tokens.length; i++) {
        if (isFree(i)  && tokens[i].matches("^\\d+\\*")) {
          String token = tokens[i].substring(0, tokens[i].length() - 1);
          try {
            int portion = Integer.parseInt(token);
            portions += portion;
          } catch (NumberFormatException e) {
            if (log.isWarnEnabled()) {
              log.warn("NumberFormatException parsing " + tokens[i]);
            }
          }
        }
      }
      //  2. calc and set portion
      if (portions > 0) {
        int widthForPortions = getSpaceLeft();
        for (int i = 0; i < tokens.length; i++) {
          if (isFree(i)  && tokens[i].matches("^\\d+\\*")) {
            String token = tokens[i].substring(0, tokens[i].length() - 1);
            try {
              int portion = Integer.parseInt(token);
              float w = (float) widthForPortions / portions * portion;
              update(Math.round(w), i);
              if (log.isDebugEnabled()) {
                log.debug("set column " + i + " from " + tokens[i]
                    + " to with " + w + " == " + Math.round(w) + "px");
              }
            } catch (NumberFormatException e) {
              if (log.isWarnEnabled()) {
                log.warn("NumberFormatException parsing " + tokens[i]);
              }
            }
          }
        }
      }
    }
  }

  public void parseAsterisks() {
    String[] tokens = getLayoutTokens();
    if (columnsLeft()) {
      //  1. count unset columns
      int portions = 0;
      for (int i = 0; i < tokens.length; i++) {
        if (isFree(i) && tokens[i].equals("*")) {
          portions++;
        }
      }
      //  2. calc and set portion
      int widthPerPortion;
      if (portions > 0) {
        widthPerPortion = getSpaceLeft() / portions;
        for (int i = 0; i < tokens.length; i++) {
          if (isFree(i) && tokens[i].equals("*")) {
            int w = widthPerPortion;
            update(w, i);
            if (log.isDebugEnabled()) {
              log.debug("set column " + i + " from " + tokens[i]
                  + " to with " + w);
            }
          }
        }
      }
    }
  }

  public void parseColumnLayout(double space){
    parseColumnLayout(space,  0);
  }

  public void parseColumnLayout(
      double space, int padding){

    if (hasLayoutTokens()) {
      parseHides(padding);
      parsePixels();
      parsePercent(space);
      parsePortions();
      parseAsterisks();
      handleSpaceLeft();
    }

    if (columnsLeft() && log.isWarnEnabled()) {
      handleIllegalTokens();
    }
  }


}

