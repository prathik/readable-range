package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BinarySearchReadableRangeBuilder implements IHumanReadableRangeBuilder {
  private final List<Integer> input;
  private final int size;

  public BinarySearchReadableRangeBuilder(final List<Integer> input) {
    this.input = Objects.requireNonNull(input);
    this.size = input.size();

    if (size == 0) {
      throw new IllegalArgumentException("Invalid input");
    }
  }

  @Override
  public List<String> toRange() {
    var ranges = new ArrayList<String>();
    var startIndex = 0;
    while (startIndex < size) {
      var endIndex = getConsecutiveRangeEnd(startIndex);
      if (endIndex == startIndex) {
        ranges.add(String.format("[%d]", input.get(startIndex)));
      } else {
        ranges.add(String.format("[%d-%d]", input.get(startIndex), input.get(endIndex)));
      }

      startIndex = endIndex + 1;
    }

    return ranges;
  }

  int getConsecutiveRangeEnd(int startIndex) {
    var value = input.get(startIndex);
    var end = size - 1;
    var start = startIndex;
    while (start < end) {
      var mid = (end + start) / 2;

      // From the starting point ie the startIndex this is the
      // value expected here if it was a sequence.
      var expectedValue = value + (mid - startIndex);

      if (expectedValue == input.get(mid)) {
        // Check whether mid is the last element.
        if (mid == (size - 1)) {
          return mid;
        } else {
          // Check that mid is the end of the sequence
          if ((input.get(mid) + 1) == input.get(mid+1)) {
            // Mid is not the end of the sequence, we now search between
            // the next element to mid and the end.
            start = mid + 1;
          } else {
            // Mid is the end of the sequence, target found
            return mid;
          }
        }
      } else if (expectedValue < input.get(mid)) {
        // Expected value is less than the value present at mid,
        // we now search between start and the element before mid.
        end = mid - 1;
      }
    }

    return end;
  }
}
