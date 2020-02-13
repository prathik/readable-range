package test;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

public class HumanReadableRangeBuilderTest {
  @Test
  public void toRange_singleRange_getOneRange() {
    var builder = new HumanReadableRangeBuilder(List.of(1, 2, 3));
    var ranges = builder.toRange();
    assertEquals(1, ranges.size());
    assertEquals("[1-3]", ranges.get(0));
  }

  @Test
  public void toRange_singleElement_getOneElement() {
    var builder = new HumanReadableRangeBuilder(List.of(1));
    var ranges = builder.toRange();
    assertEquals(1, ranges.size());
    assertEquals("[1]", ranges.get(0));
  }

  @Test
  public void toRange_twoRanges_getTheRangeInOutput() {
    var builder = new HumanReadableRangeBuilder(List.of(1, 2, 3, 7, 8, 9));
    var ranges = builder.toRange();
    assertEquals(2, ranges.size());
    assertEquals("[1-3]", ranges.get(0));
    assertEquals("[7-9]", ranges.get(1));
  }

  @Test
  public void toRange_threeRangeMidSingle_getThreeRanges() {
    var builder = new HumanReadableRangeBuilder(List.of(1, 2, 3, 5, 7, 8, 9));
    var ranges = builder.toRange();
    assertEquals(3, ranges.size());
    assertEquals("[1-3]", ranges.get(0));
    assertEquals("[5]", ranges.get(1));
    assertEquals("[7-9]", ranges.get(2));
  }

  @Test
  public void toRange_threeRangeOnlyMultiRange_getThreeRanges() {
    var builder = new HumanReadableRangeBuilder(List.of(1, 2, 3, 5, 6, 7, 8, 9, 100, 101));
    var ranges = builder.toRange();
    assertEquals(3, ranges.size());
    assertEquals("[1-3]", ranges.get(0));
    assertEquals("[5-9]", ranges.get(1));
    assertEquals("[100-101]", ranges.get(2));
  }

  @Test
  public void toRange_largeRange_expectedOutput() {
    var builder =
        new HumanReadableRangeBuilder(
            List.of(
                1, 2, 3, 8, 9, 10, 67, 68, 69, 70, 100, 101, 102, 104, 106, 201, 202, 203, 204));
    var ranges = builder.toRange();
    assertEquals("[1-3]", ranges.get(0));
    assertEquals("[8-10]", ranges.get(1));
    assertEquals("[67-70]", ranges.get(2));
    assertEquals("[100-102]", ranges.get(3));
    assertEquals("[104]", ranges.get(4));
    assertEquals("[106]", ranges.get(5));
    assertEquals("[201-204]", ranges.get(6));
  }
}
