/**
 * Copyright Siemens AG, 2016, 2019
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.executor;

/**
 * Enables the choice between different FlatZinc solvers (like Gecode, Chuffed, ...)
 * 
 * @author Copyright Siemens AG, 2016, 2019
 */
public enum FlatZincSolver {

  GECODE("-Ggecode", "fzn-gecode", "-time"),
  CHUFFED("-Gchuffed", "fzn-chuffed", "--time-out");

  private String compilerFlag;
  private String solverName;
  private String timeOutFlag;

  /**
   * @return the option to be passed to {@link MznToFznExecutable} to adapt it to this solver.
   */
  public String getCompilerFlag() {
    return compilerFlag;
  }

  /**
   * @return the name of the solver's executable
   */
  public String getSolverName() {
    return solverName;
  }

  public String getTimeOutFlag() {
    return timeOutFlag;
  }

  private FlatZincSolver(String compilerFlag, String solverName, String timeOutFlag) {
    this.compilerFlag = compilerFlag;
    this.solverName = solverName;
    this.timeOutFlag = timeOutFlag;
  }

}
