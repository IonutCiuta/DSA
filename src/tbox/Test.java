package tbox;

import java.util.*;

/**
 * Ionut Ciuta on 11/20/2016.
 */
public class Test {
    public static void main(String[] args) {
        TBox tBox = new TBox();
        tBox.define(new Concept("Woman"), new Definition(new Concept("Person"), LogicalOperator.AND, new Concept("Female")));
        tBox.define(new Concept("Man"), new Definition(new Concept("Person"), LogicalOperator.AND, new Concept("Woman", true)));
        tBox.define(new Concept("Mother"), new Definition(new Concept("Woman"), LogicalOperator.AND, new Property("hasChild", Operator.EXISTS, new Concept("Person"))));
        tBox.define(new Concept("Father"), new Definition(new Concept("Man"), LogicalOperator.AND, new Property("hasChild", Operator.EXISTS, new Concept("Person"))));
        tBox.define(new Concept("Parent"), new Definition(new Concept("Mother"), LogicalOperator.OR, new Concept("Father")));
        tBox.define(new Concept("Grandmother"), new Definition(new Concept("Woman"), LogicalOperator.AND, new Property("hasChild", Operator.EXISTS, new Concept("Parent"))));
        tBox.define(new Concept("Grandmother"), new Definition(new Concept("Woman"), LogicalOperator.AND, new Property("hasChild", Operator.EXISTS, new Concept("Parent"))));
        tBox.define(new Concept("MotherWithoutDaughter"), new Definition(new Concept("Mother"), LogicalOperator.AND, new Property("hasChild", 3, Comparator.GET)));
        tBox.define(new Concept("MotherWithManyChildren"), new Definition(new Concept("Mother"), LogicalOperator.AND, new Property("hasChild", Operator.UNIVERSAL, new Concept("Woman", true))));
        tBox.define(new Concept("Wife"), new Definition(new Concept("Woman"), LogicalOperator.AND, new Property("hasHusband", Operator.EXISTS, new Concept("Man", true))));
        tBox.display();

        ABox aBox = new ABox(tBox);
        Statement statement1 = new Statement(new Concept("MotherWithoutDaughter"), "Marry");
        aBox.evaluate(statement1);

        Statement statement2 = new Statement(new Concept("Father"), "Peter");
        aBox.evaluate(statement2);
    }
}

abstract class Component {
    String name;
}

class Concept extends Component {
    boolean negated;

    Concept() {}

    Concept(String name, boolean negated) {
        this.name = name;
        this.negated = negated;
    }

    Concept(String name) {
        this(name, false);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Concept && this.name.equals(((Concept)obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return (negated ? "-" : "") + name;
    }
}

enum Operator {
    EXISTS, UNIVERSAL
}

enum Comparator {
    LT, LET, GT, GET
}

class Property extends Component {
    Operator operator;
    int numericParam;
    Comparator comparator;
    Concept param;

    Property(String name, Operator operator, Concept param) {
        this.name = name;
        this.operator = operator;
        this.param = param;
    }

    Property(String name, int numericParam, Comparator comparator) {
        this.name = name;
        this.numericParam = numericParam;
        this.comparator = comparator;
    }

    @Override
    public String toString() {
        if(operator != null) {
            return (operator.equals(Operator.EXISTS) ? "E" : "") +
                    (operator.equals(Operator.UNIVERSAL) ? "V" : "") +
                    name + "." + param.toString();
        } else {
            return ">=" + numericParam + name;
        }
    }
}

enum LogicalOperator {
    OR, AND
}

class Definition {
    Component left;
    Component right;
    LogicalOperator operator;

    Definition(Component left, LogicalOperator operator, Component right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + (operator.equals(LogicalOperator.AND) ? " & " : " | ") + right.toString();
    }
}

class TBox {
    Map<Concept, Definition> definedConcepts = new HashMap<>();

    void define(Concept toBeDefined, Definition definition) {
        definedConcepts.put(toBeDefined, definition);
    }

    Definition get(Concept concept) {
        return definedConcepts.get(concept);
    }

    void display() {
        for(Concept concept : definedConcepts.keySet()) {
            System.out.println(concept.toString() + " = " + definedConcepts.get(concept).toString());
        }
    }
}

class Statement {
    Component component;
    String param;

    public Statement(Component component, String param) {
        this.component = component;
        this.param = param;
    }

    @Override
    public String toString() {
        return component.name + "(" + param + ")";
    }
}

class ABox {
    TBox tBox;
    List<Statement> statements;

    ABox(TBox tBox) {
        this.tBox = tBox;
        this.statements = new ArrayList<>();
    }

    void evaluate(Statement statement) {
        if (!(statement.component instanceof Concept)) return;

        Set<String> types = new HashSet<>();
        types.add(statement.component.name);
        explore(tBox.get((Concept)statement.component), types);

        System.out.println(statement.param + " : " + types);
    }

    private void explore(Definition definition, Set<String> result) {
        if(definition == null) return;
        if(definition.left instanceof Concept) {
            Concept concept = (Concept) definition.left;
            if(!concept.negated) {
                result.add(definition.left.name);
                explore(tBox.get((Concept) definition.left), result);
            }
        }
        if(definition.right instanceof Concept) {
            Concept concept = (Concept) definition.right;
            if(!concept.negated) {
                result.add(definition.right.name);
                explore(tBox.get((Concept) definition.right), result);
            }
        }
    }
}
