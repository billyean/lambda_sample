/**
 * @test
 * @summary Function tests with Function and Comparator
 * @library ../domain/
 * @(#)StringPredicatesTest.java
 * @author Tristan Yan
 * @run testng StringPredicatesTest
 */

import java.util.function.Predicate;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class StringPredicatesTest {
    private boolean isNumeric(String s) {
        try{
            Double.parseDouble(s);
            return true;
        }catch(NumberFormatException ex){
            return false;
        }
    }

    private Predicate<String> startsWith(String start) {
        return s -> s.startsWith(start);
    }

    private Predicate<String> endsWith(String end) {
        return s -> s.endsWith(end);
    }

    private Predicate<String> equalsIgnoreCase(String anotherString) {
        return s -> s.equalsIgnoreCase(anotherString);
    }

    private Predicate<String> containsString(String anotherString) {
        return s -> s.contains(anotherString);
    }

    private Predicate<String> containsCharacter(Character c) {
        return s -> s.contains(c.toString());
    }

    private Predicate<String> empty(){
        return s -> s.isEmpty();
    }

    private Predicate<String> notEmpty(){
        return empty().negate();
    }

    private Predicate<String> lessThan(String s2) {
        return s1 -> s1.compareTo(s2) < 0;
    }

    private Predicate<String> lessThanOrEqualTo(String s2) {
        return s1 -> s1.compareTo(s2) <= 0;
    }

    private Predicate<String> greaterThan(String s2) {
        return s1 -> s1.compareTo(s2) > 0;
    }

    private Predicate<String> greaterThanOrEqualTo(String s2) {
        return s1 -> s1.compareTo(s2) >= 0;
    }

    private Predicate<String> matches(String s2) {
        return s1 -> s1.matches(s2);
    }

    private Predicate<String> size(int size) {
        return s1 -> s1.length() == size;
    }

    private Predicate<String> hasLetters() {
        return s1 -> s1.chars().filter(c -> Character.isLetter(c)).findAny().isPresent();
    }

    private Predicate<String> hasDigits() {
        return s1 -> s1.chars().filter(c -> Character.isDigit(c)).findAny().isPresent();
    }

    private Predicate<String> hasLettersAndDigits() {
        return hasLetters().and(hasDigits());
    }

    private Predicate<String> hasLettersOrDigits() {
        return hasLetters().or(hasDigits());
    }

    private Predicate<String> isAlpha() {
        return s1 -> s1.chars().allMatch(c -> Character.isAlphabetic(c));
    }

    private Predicate<String> isAlphanumeric() {
        return s1 -> s1.chars().allMatch(c -> Character.isAlphabetic(c) || Character.isDigit(c));
    }

    private Predicate<String> isBlank() {
        return s1 -> s1.trim().isEmpty();
    }

    private Predicate<String> notBlank() {
        return isBlank().negate();
    }

    private Predicate<String> hasLowerCase() {
        return s1 -> s1.chars().filter(c -> Character.isLowerCase(c)).findAny().isPresent();
    }

    private Predicate<String> hasUpperCase() {
        return s1 -> s1.chars().filter(c -> Character.isUpperCase(c)).findAny().isPresent();
    }

    private Predicate<String> hasSpaces() {
        return s1 -> s1.chars().filter(c -> Character.isWhitespace(c)).findAny().isPresent();
    }

    @Test
    public void startsWith() {
        assertTrue(startsWith("Hello").test("HelloWorld"));
        assertFalse(startsWith("World").test("HelloWorld"));
    }

    @Test
    public void endsWith() {
       assertFalse(endsWith("Hello").test("HelloWorld"));
       assertTrue(endsWith("World").test("HelloWorld"));
    }

    @Test
    public void equalsIgnoreCase() {
       assertFalse(equalsIgnoreCase(null).test("HELLO"));
       assertTrue(equalsIgnoreCase("HELLO").test("hello"));
       assertTrue(equalsIgnoreCase("world").test("WORLD"));
       assertFalse(equalsIgnoreCase("Hello").test("World"));
    }

    @Test
    public void containsString() {
       assertTrue(containsString("Hello").test("WorldHelloWorld"));
       assertTrue(containsString("Hello").and(containsString("World")).test("WorldHelloWorld"));
       assertFalse(containsString("Goodbye").test("WorldHelloWorld"));
    }

    @Test
    public void containsCharacter() {
       assertTrue(containsCharacter('H').test("WorldHelloWorld"));
       assertFalse(containsCharacter('h').test("WorldHelloWorld"));
    }

    @Test
    public void emptyAndNotEmpty() {
       assertFalse(empty().test("WorldHelloWorld"));
       assertTrue(notEmpty().test("WorldHelloWorld"));
       assertTrue(empty().test(""));
       assertFalse(notEmpty().test(""));
    }

    @Test
    public void lessThan() {
       assertTrue(lessThan("b").test("a"));
       assertFalse(lessThan("b").test("b"));
       assertFalse(lessThan("b").test("c"));
    }

    @Test
    public void lessThanOrEqualTo() {
       assertTrue(lessThanOrEqualTo("b").test("a"));
       assertTrue(lessThanOrEqualTo("b").test("b"));
       assertFalse(lessThanOrEqualTo("b").test("c"));
    }

    @Test
    public void greaterThan() {
       assertFalse(greaterThan("b").test("a"));
       assertFalse(greaterThan("b").test("b"));
       assertTrue(greaterThan("b").test("c"));
    }

    @Test
    public void greaterThanOrEqualTo() {
       assertFalse(greaterThanOrEqualTo("b").test("a"));
       assertTrue(greaterThanOrEqualTo("b").test("b"));
       assertTrue(greaterThanOrEqualTo("b").test("c"));
    }

    @Test
    public void matches() {
       assertTrue(matches("a*b*").test("aaaaabbbbb"));
       assertFalse(matches("a*b").test("ba"));
    }

    @Test
    public void size() {
       assertTrue(size(1).test("a"));
       assertFalse(size(0).test("a"));
       assertTrue(size(2).test("ab"));
    }
 
    @Test
    public void hasLettersTest() {
       assertTrue(hasLetters().test("a2a"));
       assertFalse(hasLetters().test("222"));
    }

    @Test
    public void hasDigitsTest() {
       assertFalse(hasDigits().test("aaa"));
       assertTrue(hasDigits().test("a22"));
    }

    @Test
    public void hasLettersAndDigitsTest() {
        Predicate<String> predicate = hasLettersAndDigits();
        assertTrue(predicate.test("a2a"));
        assertFalse(predicate.test("aaa"));
        assertFalse(predicate.test("222"));
    }

    @Test
    public void hasLettersOrDigitsTest() {
        Predicate<String> predicate = hasLettersOrDigits();
        assertTrue(predicate.test("a2a"));
        assertTrue(predicate.test("aaa"));
        assertTrue(predicate.test("222"));
    }

    @Test
    public void isAlphaTest() {
        Predicate<String> predicate = isAlpha();
        assertTrue(predicate.test("aaa"));
        assertFalse(predicate.test("a2a"));
    }

    @Test
    public void isAlphaNumericTest() {
        Predicate<String> predicate = isAlphanumeric();
        assertTrue(predicate.test("aaa"));
        assertTrue(predicate.test("a2a"));
    }

    @Test
    public void isBlankTest() {
        Predicate<String> predicate = isBlank();
        assertTrue(predicate.test(""));
        assertTrue(predicate.test(" "));
    }

    @Test
    public void notBlankTest() {
        Predicate<String> predicate = notBlank();
       assertFalse(predicate.test(""));
       assertFalse(predicate.test(" "));
       assertTrue(predicate.test("a2a"));
    }

    @Test
    public void isNumeric() {
        Predicate<String> predicate = s -> isNumeric(s);
        assertTrue(predicate.test("222"));
        assertFalse(predicate.test("a2a2a2"));
        assertTrue(predicate.test("-.222d"));
        assertFalse(predicate.test("aaa"));
    }

    @Test
    public void hasLowerCaseTest() {
        Predicate<String> predicate = hasLowerCase();
       assertTrue(predicate.test("aaa"));
       assertFalse(predicate.test("AAA"));
    }

    @Test
    public void hasUpperCaseTest() {
        Predicate<String> predicate = hasUpperCase();
       assertFalse(predicate.test("aaa"));
       assertTrue(predicate.test("AAA"));
    }

    @Test
    public void hasSpacesTest() {
        Predicate<String> predicate = hasSpaces();
       assertTrue(predicate.test("a a a"));
       assertTrue(predicate.test(" "));
       assertTrue(predicate.test("a\tb"));
       assertTrue(predicate.test("ab\n"));
       assertFalse(predicate.test("aaa"));
    }
}
