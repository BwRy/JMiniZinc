/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.diag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import org.junit.Test;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.elements.include.IncludeItem;
import at.siemens.ct.jmz.expressions.bool.BooleanConstant;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * @author Copyright Siemens AG, 2016-2017
 */
public class TestQuickXPlain {

	@Test
  public void testConsistencyChecker_2() throws DiagnosisException {
		ConsistencyChecker checker = new ConsistencyChecker();
		LinkedHashSet<Constraint> constraintsSetC = new LinkedHashSet<>();
		File mznFile = new File("testFiles" + File.separator + "testConflictDetection2.mzn");

		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);

		BooleanExpression expression3 = new RelationalOperation<>(x2, RelationalOperator.EQ, x1);
		Constraint c3 = new Constraint("group", "c3 {x2 = x1}", expression3);
		constraintsSetC.add(c3);

		BooleanExpression expression4 = new RelationalOperation<>(x3, RelationalOperator.EQ, x2);
		Constraint c4 = new Constraint("group", "c4 {x3 = x2}", expression4);
		constraintsSetC.add(c4);

		BooleanExpression expression5 = new RelationalOperation<>(x3, RelationalOperator.GT, new IntegerConstant(2));
		Constraint c5 = new Constraint("group", "c5 {x3 > 2}", expression5);
		constraintsSetC.add(c5);

    boolean isConsistent = checker.isConsistent(constraintsSetC, Arrays.asList(new IncludeItem(mznFile)));
		assertTrue(isConsistent);
	}

	@Test
  public void testConsistencyChecker_2WithOtherConstraints() throws DiagnosisException {
		ConsistencyChecker checker = new ConsistencyChecker();
		LinkedHashSet<Constraint> constraintsSetC = new LinkedHashSet<>();
		File mznFile = new File("testFiles" + File.separator + "testConflictDetection2.mzn");

		Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
		IntegerVariable x1 = new IntegerVariable("x1", setOneTwoThree);
		IntegerVariable x2 = new IntegerVariable("x2", setOneTwoThree);
		IntegerVariable x3 = new IntegerVariable("x3", setOneTwoThree);

		BooleanExpression expression4 = new RelationalOperation<>(x2, RelationalOperator.EQ, x1);
		Constraint c4 = new Constraint("group", "c4 {x2 = x1}", expression4);
		constraintsSetC.add(c4);

		BooleanExpression expression5 = new RelationalOperation<>(x3, RelationalOperator.EQ, x2);
		Constraint c5 = new Constraint("group", "c5 {x3 = x2}", expression5);
		constraintsSetC.add(c5);

		BooleanExpression expression6 = new RelationalOperation<>(x3, RelationalOperator.GT, x2);
		Constraint c6 = new Constraint("group", "c6 {x3 > 2}", expression6);
		constraintsSetC.add(c6);

    boolean isConsistent = checker.isConsistent(constraintsSetC, Arrays.asList(new IncludeItem(mznFile)));
		assertFalse(isConsistent);
	}

	@Test
  public void testQuickXPlainMinCS_2() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset2(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);

		// the expected out is (c2,c3,c4,c5)
		assertTrue(minCS.size() == 4);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(1)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(2)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(3)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(4)));
	}

	@Test
  public void testQuickXPlainMinCS_2WithMoreConstraints() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset2WithMoreConstraints(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);

		//the expected output is c5, c6
		assertTrue(minCS.size() == 2);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(4)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(5)));
	}
	
	@Test
  public void testQuickXPlainMinCS_NoConflict() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset2NoConflict(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
    assertNull(minCS);
	}

	@Test
  public void testQuickXPlainMinCS_5() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset1(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);

		// the expected output is c2
		assertTrue(minCS.size() == 1);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(1)));
	}

	@Test
  public void testQuickXPlainMinCS_6() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset6(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);
		assertTrue(minCS.size() == 2);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(0)));
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(1)));
	}

	@Test
  public void testQuickXPlainMinCS_8() throws FileNotFoundException, DiagnosisException {
		java.util.Set<Constraint> minCS = null;
		java.util.Set<Constraint> constraintsSetC = new LinkedHashSet<Constraint>();
		java.util.Set<Element> decisionsVar = new LinkedHashSet<Element>();
		String fileName = UtilsForTest.getTestDataset8(constraintsSetC, decisionsVar);
		AbstractConflictDetection conflictDetection = new QuickXPlain(fileName);

		minCS = conflictDetection.getMinConflictSet(constraintsSetC);
		assertNotNull(minCS);

		// expected output is c4
		assertTrue(minCS.size() == 1);
		assertTrue(minCS.contains(new ArrayList<>(constraintsSetC).get(3)));
	}

  @Test
  public void testQuickXPlain_InconsistentKB_EmptyC() throws FileNotFoundException, DiagnosisException {
    testQuickXPlain_InconsistentKB(Collections.emptySet());
  }

  @Test
  public void testQuickXPlain_InconsistentKB_NonEmptyC() throws FileNotFoundException, DiagnosisException {
    testQuickXPlain_InconsistentKB(new LinkedHashSet<Constraint>(Arrays.asList(new Constraint(BooleanConstant.TRUE))));
  }

  private void testQuickXPlain_InconsistentKB(java.util.Set<Constraint> constraintsSetC)
      throws FileNotFoundException, DiagnosisException {
    Constraint inconsistentConstraint = new Constraint(
        new RelationalOperation<Integer>(new IntegerConstant(1), RelationalOperator.EQ, new IntegerConstant(2)));
    Collection<Element> inconsistentKB = Arrays.asList(inconsistentConstraint);
    AbstractConflictDetection conflictDetection = new QuickXPlain(null, inconsistentKB);

    java.util.Set<Constraint> minCS = conflictDetection.getMinConflictSet(constraintsSetC);
    assertNotNull(minCS);
    assertTrue(minCS.toString(), minCS.isEmpty());
  }
}
