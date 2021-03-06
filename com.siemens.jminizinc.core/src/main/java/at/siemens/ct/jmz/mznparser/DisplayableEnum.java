package at.siemens.ct.jmz.mznparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.set.StringSetExpression;
import at.siemens.ct.jmz.expressions.string.StringConstant;

public class DisplayableEnum implements Displayable, Expression<String> {

  private final String name;
  private Set<String> range;


  public DisplayableEnum(String name, Set<String> range) {
    this.name = name;
    this.range = range;
  }

  @Override
  public List<Constraint> createConstraint(String value) {
    if (value.equals("Undefined") || value.isEmpty())
      return null;

    if (!range.contains(value)){
      throw new IllegalArgumentException("Value not in domain: " + value);
    }

    BooleanExpression expression = new RelationalOperation<>(this, RelationalOperator.EQ,
      new StringConstant(new StringSetExpression(range), value));

    Constraint constraint = new Constraint("userDefined",
      String.format("%s = %s", this.getInfo().get(0).getLabelCaption(), value), expression);

    return Collections.singletonList(constraint);
  }

  @Override
  public List<InfoGUI> getInfo() {
    ArrayList<String> values = new ArrayList<>();
    values.add("Undefined");
    values.addAll(range);
    return Collections.singletonList(new InfoGUI(name, name, ComponentType.CHOICE, values));
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String use() {
    return getName();
  }

  @Override
  public Expression<String> substitute(String name, Object value) {
    return null;
  }

  @Override
  public String value() {
    return null;
  }
}
