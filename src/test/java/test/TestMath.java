package test;

import org.example.MathService;
import org.junit.Test;

import java.sql.SQLException;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestMath {
    @Test
     public void findAllBySolutionTest() throws SQLException {
        assertFalse(MathService.findAllBySolution(6).stream().noneMatch(el -> el.getX() == 6));
    }
    @Test
    public void isSolutionTest() throws SQLException {
        assertTrue(MathService.isSolution("2*x+5=17", 6));
    }
    @Test
    public void hasValidBracketsTest()  {
        assertTrue(MathService.hasValidBrackets("(2*x+5)=17"));
    }
    @Test
    public void hasValidSymbolsTest()  {
        assertTrue(MathService.hasValidSymbols("(2*x*-5)=17"));
    }

}
