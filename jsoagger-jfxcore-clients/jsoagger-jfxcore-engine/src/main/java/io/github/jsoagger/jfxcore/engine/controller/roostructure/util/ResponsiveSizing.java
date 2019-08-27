package io.github.jsoagger.jfxcore.engine.controller.roostructure.util;

import io.github.jsoagger.jfxcore.api.IResponsiveSizing;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ResponsiveSizing implements IResponsiveSizing {

  //If area has fiexd size, parent width is realParentWidth - fixedSize
  // And widthPercent i calculated from this size
  private double parentWidth;

  private double widthPercent;
  private double fixedWidth;

  private double width;
  private double height;
  private boolean hide;
  private boolean minimize;


  /**
   * Constructor
   */
  public ResponsiveSizing() {}


  public double getFixedWidth() {
    return fixedWidth;
  }


  public boolean isHide() {
    return hide;
  }


  public boolean isMinimize() {
    return minimize;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#of(double)
   */
  @Override
  public void of(double parentWidth) {
    this.parentWidth = parentWidth;
    this.width = widthPercent * parentWidth;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#getWidthPercent()
   */
  @Override
  public double getWidthPercent() {
    return widthPercent;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#setWidthPercent(double)
   */
  @Override
  public void setWidthPercent(double widthPercent) {
    this.widthPercent = widthPercent;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#getWidth()
   */
  @Override
  public double getWidth() {
    if (fixedWidth > 0) {
      return fixedWidth;
    }
    return widthPercent * parentWidth;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#setWidth(double)
   */
  @Override
  public void setWidth(double width) {
    this.width = width;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#getHeight()
   */
  @Override
  public double getHeight() {
    return height;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#setHeight(double)
   */
  @Override
  public void setHeight(double height) {
    this.height = height;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#isToHide()
   */
  @Override
  public boolean isToHide() {
    return hide;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#setHide(boolean)
   */
  @Override
  public void setHide(boolean hide) {
    this.hide = hide;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#isToMinimize()
   */
  @Override
  public boolean isToMinimize() {
    return minimize;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#setMinimize(boolean)
   */
  @Override
  public void setMinimize(boolean minimize) {
    this.minimize = minimize;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#getParentWidth()
   */
  @Override
  public double getParentWidth() {
    return parentWidth;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#setParentWidth(double)
   */
  @Override
  public void setParentWidth(double parentWidth) {
    this.parentWidth = parentWidth;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#setFixedWidth(double)
   */
  @Override
  public void setFixedWidth(double fixedWidth) {
    this.fixedWidth = fixedWidth;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.util.IResponsiveSizing#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ResponsiveSizing [parentWidth=");
    builder.append(parentWidth);
    builder.append(", widthPercent=");
    builder.append(widthPercent);
    builder.append(", fixedWidth=");
    builder.append(fixedWidth);
    builder.append(", width=");
    builder.append(width);
    builder.append(", height=");
    builder.append(height);
    builder.append(", hide=");
    builder.append(hide);
    builder.append(", minimize=");
    builder.append(minimize);
    builder.append("]");
    return builder.toString();
  }
}
