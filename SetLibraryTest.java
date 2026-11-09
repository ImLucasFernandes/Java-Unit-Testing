import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a text assignment used to simulate writing java unit tests. 
 * The use of lambda functions is from the lab syntax, the use of nested lambda functions and premptive timeout
 * clauses was dervied from the inclass discussion on timeout solutions on 2026-10-23
 * all bug cases and descriptions:
 * <p>
 * TestCreateSetNegativeMin() shows a bug through showing that a negative min
 * results in a range of numbers that is greater or equal to the max which is outside the
 * commented limits of the function.
 * line 33 ought to be fixed by include a requirement that all integer values be greater than 0
 * FIX: else if(minimum > maximum || minimum < 0){
 * <p>
 * TestIsSetUniqueArraySize1() shows a bug when you have an integer array size of 1, an index out of bounds
 * exception should be thrown due to the inner for loop requiring at least 2 index values to compare with eachother
 * without stepping outside the index size.
 * after line 71 should include another boolean condition of the
 * index size also being greater than/equal to 2
 * FIX   if(arraySet == null || arraySet.size() == 0){...})
 * else if (arraySet.size() < 2) {
 * throw new IllegalArgumentException("array size must be greater than 2");
 * }
 * <p>
 * TestCreateIntersectionSetbEmpty() shows a bug that occurs when you have an empty setB array.
 * the inner for loop that runs through the condition of the size of that array just immediately terminates
 * this results in the return of an empty array instead of an illegal argument exception that should be thrown
 * the illegal argument exception message clearly states that both arguements cant be null or empty
 * line 98 should also inlucde a condition stating that the size of array setB being greater than 0
 * FIX   if (setA == null || setB == null || setA.size() == 0 || setB.size() == 0 ) {
 * <p>
 * TestisSubSetSetBFalse1OverlappedIndex() demonstrates an error within the method taht tests if setB is a subset of A.
 * the entire array of setB is required to be within setA for it to be a subset however even one common index value
 * in both arrays will return a true value. it only returns false if there are no overlapping index values
 * which is seen in  TestisSubSetSetBFalseNoOverlap()
 * line 143 to 145 is the issue. it returns true if any of the index values are equal.
 * the solution requires a far more complex program that takes the subset array and compares it to a
 * created array of the same size within the larger array (setA) starting at the first A index then moving forward
 * till you reach the end of a.
 * <p>
 * TestCreateSetSizeEquals0() times out proving a bug because it doesnt catch the zero size variable
 * value earlier resulting in the while loop within the structure that adds to the array
 * continuing infinitely because the condition on line 55 never equates to true terminating the loop
 * regardless of whats actually unique or not.
 * to fix this i would change the condition on line 30 to be less than  1 to avoid this issue entirely
 * FIX:         if(size < 0){ -> if(size < 1){
 * <p>
 * TestCreateSetUniqueEnoughValues() demonstrates another potential bug. it passes validation because
 * the maximum minus the mininum is equal to the size not smaller but due to their being the unique condition
 * it creates random numbers till the array is filled up but there isnt enough space within the array
 * to guarantee a unique set. the test uses the other method to determin if its unique and returns false.
 * even through the parameter was set to true.
 * to fix this i would stipulate that the condition on line 36 be modified to include <= size to ensure
 * that theres enough spaces for unique entries only
 * FIX: else if(uniqueElements && maximum-minimum < size){ -> else if(uniqueElements && maximum-minimum <= size){
 * it creates the array
 */
class SetLibraryTest {
    /**
     * Tests an IllegalArguementException for when the entered size is less than 0
     * used for validation verification
     */
    @Test
    public void TestCreateSetSizeLessThan0() {
        /*Test Data*/
        int size = -1;
        int minimum = 1;
        int maximum = 10;
        boolean uniqueElements = true;
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> SetLibrary.createSet(size, minimum, maximum, uniqueElements)));
    }

    /**
     * Tests an IllegalArguementException for when the entered size is = 0, timesout
     * used for validation verification
     */
    @Test
    public void TestCreateSetSizeEquals0() {
        /*Test Data*/
        int size = 0;
        int minimum = 1;
        int maximum = 10;
        boolean uniqueElements = true;
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> SetLibrary.createSet(size, minimum, maximum, uniqueElements)));

    }

    /**
     * Tests an IllegalArguementException for when the min is greater than the max
     */
    @Test
    public void TestCreateSetMinGreaterMax() {
        /*Test Data*/
        int size = 5;
        int minimum = 15;
        int maximum = 5;
        boolean uniqueElements = true;
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> SetLibrary.createSet(size, minimum, maximum, uniqueElements)));


    }

    /**
     * Tests for a size of 1, should result in a unique set with a single value. and
     * serve as a verifiction for the fix regarding the TestCreateSetSizeEquals0() bug
     */
    @Test
    public void TestCreateSetSizeValue1() {
        /*Test Data*/
        int size = 1;
        int minimum = 1;
        int maximum = 5;
        boolean uniqueElements = true;
        /* expected result */
        int expectedResult = 1;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createSet(size, minimum, maximum, uniqueElements));
        /*actual result*/
        int actualResult = SetLibrary.createSet(size, minimum, maximum, uniqueElements).size();
        /*checking if their equal*/
        Assertions.assertTrue(expectedResult == actualResult);


    }

    /**
     * Tests an IllegalArguementException for when the min is negative and unique is true
     * with the result being greater than the size, used for validation verification
     */
    @Test
    public void TestCreateSetNegativeMin() {
        /*Test Data*/
        int size = 10;
        int minimum = -10;
        int maximum = 5;
        boolean uniqueElements = true;
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> SetLibrary.createSet(size, minimum, maximum, uniqueElements)));

    }

    /**
     * Tests for a non unqiue set, if it allows for repeats then the size should be equal
     * to the passed parameter, just to verify functionality
     */
    @Test
    public void TestCreateSetNotUnique() {
        /*Test Data*/
        int size = 10;
        int minimum = 1;
        int maximum = 5;
        boolean uniqueElements = false;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createSet(size, minimum, maximum, uniqueElements));
        /*asserts that the function should return a size equal to the size parameter filling the index
         * actual and expected data embedded within the assertion , size var is the expected.*/
        Assertions.assertTrue(SetLibrary.createSet(size, minimum, maximum, uniqueElements).size() == size);

    }

    /**
     * Tests for a unqiue set with not enough values to a unique set should return
     * an illegal argument exception demonstrating the validation, for function verification
     */
    @Test
    public void TestCreateSetUniqueNotEnoughValues() {
        /*Test Data*/
        int size = 10;
        int minimum = 1;
        int maximum = 10;
        boolean uniqueElements = true;
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> SetLibrary.createSet(size, minimum, maximum, uniqueElements)));

    }

    /**
     * Tests for a unqiue set with enough different values for a unique set. it fails demonstrating an
     * error involving the size being equal to the number of potential unique variables
     */
    @Test
    public void TestCreateSetUniqueEnoughValues() {
        /*Test Data*/
        int size = 5;
        int minimum = 1;
        int maximum = 6;
        boolean uniqueElements = true;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createSet(size, minimum, maximum, uniqueElements));
        /*Test Data*/
        ArrayList<Integer> uniqueList = SetLibrary.createSet(size, minimum, maximum, uniqueElements);
        /*expected result*/
        boolean expectedResult = true;
        /*actual result*/
        boolean actualResult = SetLibrary.isSetUnique(uniqueList);
        /*asserts that the expected and actual results are the same*/
        Assertions.assertEquals(expectedResult, actualResult);
    }

    /**
     * Tests an IllegalArguementException for when the array is null, again for
     * validation purposes.
     */
    @Test
    public void TestIsSetUniqueNull() {
        /*Test Data*/
        ArrayList<Integer> list = null;
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> SetLibrary.isSetUnique(null)));

    }

    /**
     * Tests an IllegalArguementException for when the array is empty
     */
    @Test
    public void TestIsSetUniqueEmpty() {
        /*Test Data*/
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList());
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> SetLibrary.isSetUnique(list)));

    }

    /**
     * Tests an array size of 1 (just one numerical value in the array)
     */
    @Test
    public void TestIsSetUniqueArraySize1() {
        /*Test Data*/
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1));
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IndexOutOfBoundsException.class,
                        () -> SetLibrary.isSetUnique(list)));

    }

    /**
     * Tests for a logical true return for all unique portions of the set
     */
    @Test
    public void TestIsSetUniqueTrue() {
        /*Test Data*/
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 6));
        /*Expected result*/
        boolean expectedResult = true;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.isSetUnique(list));
        /*actual result*/
        boolean actualResult = SetLibrary.isSetUnique(list);
        /*checking if their equal*/
        Assertions.assertTrue(expectedResult == actualResult);
    }

    /**
     * Tests for a logical false return for all unique portions of the set
     */
    @Test
    public void TestIsSetUniqueFalse() {
        /*Test Data*/
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 2, 5, 6));
        /*Expected result*/
        boolean expectedResult = false;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.isSetUnique(list));
        /*actual result*/
        boolean actualResult = SetLibrary.isSetUnique(list);
        /*checking if their equal*/
        Assertions.assertTrue(expectedResult == actualResult);
    }

    /**
     * Tests for all common index values.
     */
    @Test
    public void TestCreateIntersectionAllValues() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(5, 8, 1, 10, 15));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(5, 8, 1, 10, 15));
        /*Expected result*/
        ArrayList<Integer> expectedResult = new ArrayList<Integer>(Arrays.asList(5, 8, 1, 10, 15));
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createIntersection(setA, setB));
        /*actual result*/
        ArrayList<Integer> actualResult = SetLibrary.createIntersection(setA, setB);
        /*checking if their equal*/
        Assertions.assertIterableEquals(expectedResult, actualResult);
    }

    /**
     * Tests for 3 common index values.
     */
    @Test
    public void TestCreateIntersection3Values() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(5, 10, 15, 7, 4));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(5, 8, 1, 10, 15));
        /*Expected result*/
        ArrayList<Integer> expectedResult = new ArrayList<Integer>(Arrays.asList(5, 10, 15));
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createIntersection(setA, setB));
        /*actual result*/
        ArrayList<Integer> actualResult = SetLibrary.createIntersection(setA, setB);
        /*checking if their equal*/
        Assertions.assertIterableEquals(expectedResult, actualResult);
    }

    /**
     * Tests for no common index values.
     */
    @Test
    public void TestCreateIntersection0Values() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10));
        /*Expected result*/
        ArrayList<Integer> expectedResult = new ArrayList<Integer>(Arrays.asList(5, 10, 15));
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createIntersection(setA, setB));
        /*actual result*/
        ArrayList<Integer> actualResult = SetLibrary.createIntersection(setA, setB);
        /*checking if their equal*/
        Assertions.assertNotEquals(expectedResult, actualResult);
    }

    /**
     * Tests for an empty setB being empty, should throw an illegal argument exception
     */
    @Test
    public void TestCreateIntersectionSetBEmpty() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList());
        /*Expected result and actual as part of the same instruction with 5 second timeout assertion*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> SetLibrary.createIntersection(setA, setB)));
    }

    /**
     * Tests for an empty setA being smaller than setB
     */
    @Test
    public void TestCreateIntersectionSetASmallerSetB() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        /*Expected result*/
        ArrayList<Integer> expectedResult = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createIntersection(setA, setB));
        /*actual result*/
        ArrayList<Integer> actualResult = SetLibrary.createIntersection(setA, setB);
        /*checking if their equal*/
        Assertions.assertIterableEquals(expectedResult, actualResult);
    }

    /**
     * Tests for an empty setA being greater than setB
     */
    @Test
    public void TestCreateIntersectionSetAGreaterSetB() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(1, 2, 3));
        /*Expected result*/
        ArrayList<Integer> expectedResult = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createIntersection(setA, setB));
        /*actual result*/
        ArrayList<Integer> actualResult = SetLibrary.createIntersection(setA, setB);
        /*checking if their equal*/
        Assertions.assertIterableEquals(expectedResult, actualResult);
    }

    /**
     * Tests for a massive subset, smaller subset tests verfied indirectly through tests above
     */
    @Test
    public void TestCreateIntersectionMassiveSets() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(10000, 62, 235));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(10000, 62, 235));
        /*Expected result*/
        ArrayList<Integer> expectedResult = new ArrayList<Integer>(Arrays.asList(10000, 62, 235));
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.createIntersection(setA, setB));
        /*actual result*/
        ArrayList<Integer> actualResult = SetLibrary.createIntersection(setA, setB);
        /*checking if their equal*/
        Assertions.assertIterableEquals(expectedResult, actualResult);
    }

    /**
     * Tests for setB being subset of setA
     */
    @Test
    public void TestisSubSetSetBTrueWithOverlap() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(1, 2, 3));
        /*Expected result*/
        boolean expectedResult = true;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.isSubSet(setA, setB));
        /*actual result*/
        boolean actualResult = SetLibrary.isSubSet(setA, setB);
        /*checking if their equal*/
        Assertions.assertEquals(expectedResult, actualResult);
    }

    /**
     * Tests for setB not being subset of setA with no overlapping indexes.
     */
    @Test
    public void TestisSubSetSetBFalseNoOverlap() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(7, 8, 9));
        /*Expected result*/
        boolean expectedResult = false;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.isSubSet(setA, setB));
        /*actual result*/
        boolean actualResult = SetLibrary.isSubSet(setA, setB);
        /*checking if their equal*/
        Assertions.assertEquals(expectedResult, actualResult);

    }

    /**
     * Tests for setB not being subset of setA with 1 index overlap, issue found here.
     */
    @Test
    public void TestisSubSetSetBFalse1OverlappedIndex() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(7, 8, 3));
        /*Expected result*/
        boolean expectedResult = false;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.isSubSet(setA, setB));
        /*actual result*/
        boolean actualResult = SetLibrary.isSubSet(setA, setB);
        /*checking if their equal*/
        Assertions.assertEquals(expectedResult, actualResult);

    }

    /**
     * Tests for setB and setA being of equal length but different values
     */
    @Test
    public void TestisSubSetSetBEqualLengthDiffValues() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 0));
        /*Expected result*/
        boolean expectedResult = false;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.isSubSet(setA, setB));
        /*actual result*/
        boolean actualResult = SetLibrary.isSubSet(setA, setB);
        /*checking if their equal*/
        Assertions.assertEquals(expectedResult, actualResult);

    }

    /**
     * Tests for a larger subset pool, smaller ones verified indirectly through earlier tests.
     */
    @Test
    public void TestisSubSetBigSets() {
        /*Test Data*/
        ArrayList<Integer> setA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 14, 15, 16, 17, 18));
        ArrayList<Integer> setB = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 14, 15, 16, 17, 18));
        /*Expected result*/
        boolean expectedResult = true;
        /*timeout assertion to ensure no infinite loop and test executes in timely manner*/
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> SetLibrary.isSubSet(setA, setB));
        /*actual result*/
        boolean actualResult = SetLibrary.isSubSet(setA, setB);
        /*checking if their equal*/
        Assertions.assertEquals(expectedResult, actualResult);

    }

}
