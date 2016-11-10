package at.siemens.ct.jmz.expressions.comprehension;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.integer.ArithmeticOperation;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Tests {@link Comprehension} and its concrete implementations.
 *
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestComprehension {

  /**
   * Constructs a simple {@link ListComprehension}, calls {@link ListComprehension#toString()} and checks the result.
   */
  @Test
  public void testSimpleListComprehension() throws Exception {
    testSimpleComprehension(ListComprehension.class, '[', ']');
  }

  /**
   * Constructs a simple {@link SetComprehension}, calls {@link SetComprehension#toString()} and checks the result.
   */
  @Test
  public void testSimpleSetComprehension() throws Exception {
    testSimpleComprehension(SetComprehension.class, '{', '}');
  }

  private static <C extends Comprehension<Integer>> void testSimpleComprehension(
      Class<C> comprehensionClass, char leftBracket, char rightBracket) throws Exception {
    int lb = 1, ub = 10;
    RangeExpression range = new RangeExpression(lb, ub);
    String iteratorName = "i";
    IteratorExpression<Integer> iterator = range.iterate(iteratorName);
    Generator<Integer> generator = new Generator<>(iterator);
    Constructor<C> constructor = comprehensionClass.getConstructor(Generator.class,
        Expression.class);
    C comprehension = constructor.newInstance(generator, ArithmeticOperation.plus(iterator, 2));
    String expectedOutput = String.format("%c %s + 2 | %s in %d..%d %c", leftBracket, iteratorName,
        iteratorName, lb, ub, rightBracket);
    Assert.assertEquals(expectedOutput, comprehension.use());
  }

}
