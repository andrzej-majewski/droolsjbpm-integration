package org.drools.fluent.compact.imp;

import org.drools.command.KnowledgeContextResolveFromContextCommand;
import org.drools.command.SetVariableCommand;
import org.drools.command.runtime.SetGlobalCommand;
import org.drools.command.runtime.rule.FireAllRulesCommand;
import org.drools.command.runtime.rule.InsertObjectCommand;
import org.drools.fluent.compact.FluentCompactKnowledgeBase;
import org.drools.fluent.compact.FluentCompactSimulation;
import org.drools.fluent.compact.FluentCompactStatefulKnowledgeSession;
import org.drools.fluent.compact.InternalSimulation;
import org.drools.fluent.standard.FluentStandardStatefulKnowledgeSession;
import org.drools.fluent.test.impl.AbstractFluentTest;

public class FluentCompactStatefulKnowledgeSessionImpl extends AbstractFluentTest<FluentCompactStatefulKnowledgeSession>
    implements
    FluentCompactStatefulKnowledgeSession {
    
    public FluentCompactStatefulKnowledgeSessionImpl(InternalSimulation sim) {
        super();
        setSim( sim );
    }
    
    public FluentCompactStatefulKnowledgeSession setGlobal(String identifier,
                                                            Object object) {
        getSim().addCommand( new SetGlobalCommand( identifier, object ) );
        return this;
    }        

    public FluentCompactStatefulKnowledgeSession fireAllRules() {
        getSim().addCommand( new FireAllRulesCommand() );
        return this;
    }

    public FluentCompactStatefulKnowledgeSession insert(Object object) {
        getSim().addCommand( new InsertObjectCommand( object ) );
        return this;
    }

    public FluentCompactStatefulKnowledgeSession newStep(long distance) {
        getSim().newStep( distance );
        return this;
    }

    public FluentCompactKnowledgeBase getKnowledgeBase() {
        return new FluentCompactKnowledgeBaseImpl(getSim(), this);
    }

    public FluentCompactStatefulKnowledgeSession set(String contextName, String variableName) {
        getSim().addCommand( new SetVariableCommand( contextName, variableName ) );
        return this;
    }
    
    public FluentCompactStatefulKnowledgeSession set(String name) {
        getSim().addCommand( new SetVariableCommand( null, name ) );
        return this;
    }

    public FluentCompactSimulation end() {
        return (FluentCompactSimulation) getSim();
    }

}
