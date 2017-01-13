/**
 * Copyright � Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

/**
 * @author � Siemens AG, 2016
 */
public class Implication extends BinaryLogicalOperation {

  public Implication(BooleanExpression antedecent, BooleanExpression consequent) {
    super(antedecent, BinaryLogicalOperator.Implies, consequent);
  }

}