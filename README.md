# JMiniZinc

JMiniZinc is a Java interface to the constraint modeling language [MiniZinc](http://www.minizinc.org/).
It provides an API to generate MiniZinc models, to start MiniZinc compilers and solvers (which are not part of JMiniZinc), and to parse the output of solvers.

## Project Structure

JMiniZinc is separated into three subprojects:
* [`core`](com.siemens.jminizinc.core) contains the core functionality for generating and solving MiniZinc models.
* [`diag`](com.siemens.jminizinc.diag) contains various tools for model-based diagnosis.
* [`diag.ui`](com.siemens.jminizinc.diag.ui) adds a user interface on top of the `diag` component.

## Building

JMiniZinc uses [Maven](https://maven.apache.org) to manage its building process. Executing

```bash
$ mvn clean install
```

in any of the subprojects will automatically fetch all dependencies (declared in [`pom.xml`](/com.siemens.jminizinc/pom.xml)) and compile the project.
Executing this command in the [`com.siemens.jminizinc`](com.siemens.jminizinc) subfolder will build all components at once.

Artifacts generated will be placed in the `target/` folders in the subprojects. Most notably you'll find there JAR files called `core-1.2.jar`, `diag-1.2.jar` and so on.

## Usage

You should be able to figure out quickly how to use JMiniZinc by looking at our [JUnit tests](/com.siemens.jminizinc.core/src/test/java/at/siemens/ct/jmz), especially [NQueensDemo](https://github.com/siemens/JMiniZinc/blob/master/com.siemens.jminizinc.core/src/test/java/at/siemens/ct/jmz/NQueensDemo.java).
First you construct a MiniZinc model by adding [`Variable`](/com.siemens.jminizinc.core/src/main/java/at/siemens/ct/jmz/elements/Variable.java)s, [`Constraint`](/com.siemens.jminizinc.core/src/main/java/at/siemens/ct/jmz/elements/constraints/Constraint.java)s etc. to a [`ModelBuilder`](/com.siemens.jminizinc.core/src/main/java/at/siemens/ct/jmz/IModelBuilder.java),
Then you add additional syntactical elements (e.g. a solving strategy) to a [`ModelWriter`](/com.siemens.jminizinc.core/src/main/java/at/siemens/ct/jmz/writer/IModelWriter.java)
and let the resulting model be solved by an [`Executor`](/com.siemens.jminizinc.core/src/main/java/at/siemens/ct/jmz/executor/IExecutor.java).
For the Executors to work, executables `minizinc`, `mzn2fzn`, ... should be available on your `PATH`.

## Development Status

JMiniZinc is still in development, so you´ll stumble upon some inline TODOs when looking through the code.
However, it is stable enough for many use cases and has been successfully used in various projects already.
Feel free to contribute by fixing open TODOs or adding features!

## References

The API design follows the official [MiniZinc specification](http://www.minizinc.org/doc-lib/minizinc-spec.pdf).