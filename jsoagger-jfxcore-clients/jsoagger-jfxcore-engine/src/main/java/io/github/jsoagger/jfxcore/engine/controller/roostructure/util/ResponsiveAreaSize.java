package io.github.jsoagger.jfxcore.engine.controller.roostructure.util;

import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;

/**
 * Responsive configuration of rootstructure between screen resolution X and Y. For this intervall,
 * we support only that one area of the screen with a fixed size , for a given screen size
 * intervall.
 * <p>
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ResponsiveAreaSize implements IResponsiveAreaSize {

  private double minValue = -1L;
  private double maxValue = Double.MAX_VALUE;

  private double fixedAreaSize = 0;
  private List<IResponsiveSizing> sizes = new ArrayList<>();


  /**
   * Constructor
   */
  public ResponsiveAreaSize() {}


  public double getMinValue() {
    return minValue;
  }


  public void setMinValue(double minValue) {
    this.minValue = minValue;
  }


  public double getMaxValue() {
    return maxValue;
  }


  public void setMaxValue(double maxValue) {
    this.maxValue = maxValue;
  }


  public List<IResponsiveSizing> getSizes() {
    return sizes;
  }


  public void setSizes(List<IResponsiveSizing> sizes) {
    this.sizes = sizes;
  }

  @Override
  public double getTotalWidth() {
    double total = 0.0;
    for (IResponsiveSizing responsiveSizing : sizes) {
      total += responsiveSizing.getWidth();
    }

    return total;
  }


  @Override
  public IResponsiveSizing getSizeOf(int colIndex) {
    return sizes.get(colIndex);
  }


  @Override
  public void of(double parentWidth) {
    for(IResponsiveSizing s: sizes) {
      s.of(parentWidth);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ResponsiveAreaSize [minValue=");
    builder.append(minValue);
    builder.append(", maxValue=");
    builder.append(maxValue);
    builder.append(", ");
    if (sizes != null) {
      builder.append("sizes=");
      builder.append(sizes);
    }
    builder.append("]");
    return builder.toString();
  }


  @Override
  public double getFixedAreaSize() {
    return fixedAreaSize;
  }


  @Override
  public void setFixedAreaSize(double fixedAreaSize) {
    this.fixedAreaSize = fixedAreaSize;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(maxValue);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(minValue);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ResponsiveAreaSize other = (ResponsiveAreaSize) obj;
    if (Double.doubleToLongBits(maxValue) != Double.doubleToLongBits(other.maxValue)) {
      return false;
    }
    if (Double.doubleToLongBits(minValue) != Double.doubleToLongBits(other.minValue)) {
      return false;
    }
    return true;
  }
}
