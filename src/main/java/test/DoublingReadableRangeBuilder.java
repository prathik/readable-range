package test;

import java.util.ArrayList;
import java.util.List;

public class DoublingReadableRangeBuilder implements IHumanReadableRangeBuilder {
  private final List<Integer> input;

  public DoublingReadableRangeBuilder(final List<Integer> input) {
    this.input = input;
  }

  private int getConsecutiveRangeEnd(int startIndex, int start, int end) {
    if (start >= end) {
      return end;
    }

    int c = 0;

    var prevStart = start;

    while (start <= end && input.get(start).equals(input.get(startIndex) + start - startIndex)) {
      if (start == input.size() - 1 || input.get(start) + 1 < input.get(start + 1)) return start;

      prevStart = start;
      start = Math.min(start + (++c * 2), end);
    }

    return getConsecutiveRangeEnd(startIndex, prevStart, --start);
  }

  @Override
  public List<String> toRange() {
    var startIndex = 0;
    var o = new ArrayList<String>();
    while (startIndex < input.size()) {
      var nextIndex = getConsecutiveRangeEnd(startIndex, startIndex + 1, input.size() - 1);
      var s = input.get(startIndex);
      var e = input.get(nextIndex);
      o.add("[" + s + ((nextIndex - startIndex) > 0 ? "-" + e + "]" : "]"));
      startIndex = ++nextIndex;
    }
    return o;
  }
}
