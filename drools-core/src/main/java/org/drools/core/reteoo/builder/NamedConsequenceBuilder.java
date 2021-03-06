package org.drools.core.reteoo.builder;

import org.drools.core.ActivationListenerFactory;
import org.drools.core.common.UpdateContext;
import org.drools.core.definitions.rule.impl.RuleImpl;
import org.drools.core.reteoo.RuleTerminalNode;
import org.drools.core.reteoo.TerminalNode;
import org.drools.core.rule.GroupElement;
import org.drools.core.rule.NamedConsequence;
import org.drools.core.rule.RuleConditionElement;

public class NamedConsequenceBuilder implements ReteooComponentBuilder {

    public void build(BuildContext context, BuildUtils utils, RuleConditionElement rce) {
        NamedConsequence namedConsequence = (NamedConsequence) rce;
        RuleTerminalNode terminalNode = buildTerminalNodeForNamedConsequence(context, namedConsequence);

        terminalNode.attach(context);

        terminalNode.networkUpdated(new UpdateContext());

        // adds the terminal node to the list of nodes created/added by this sub-rule
        context.getNodes().add( terminalNode );
    }

    public boolean requiresLeftActivation(BuildUtils utils, RuleConditionElement rce) {
        return false;
    }

    static RuleTerminalNode buildTerminalNodeForNamedConsequence(BuildContext context, NamedConsequence namedConsequence) {
        RuleImpl rule = context.getRule();
        GroupElement subrule = (GroupElement) context.peek();

        ActivationListenerFactory factory = context.getKnowledgeBase().getConfiguration().getActivationListenerFactory( rule.getActivationListener() );
        TerminalNode terminal = factory.createActivationListener( context.getNextId(),
                                                                  context.getTupleSource(),
                                                                  rule,
                                                                  subrule,
                                                                  0, // subruleIndex,
                                                                  context );

        RuleTerminalNode terminalNode = (RuleTerminalNode) terminal;
        ((RuleTerminalNode) terminal).setConsequenceName( namedConsequence.getConsequenceName() );
        return terminalNode;
    }
}
