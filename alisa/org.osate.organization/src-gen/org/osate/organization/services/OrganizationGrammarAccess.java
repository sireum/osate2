/*
* generated by Xtext
*/
package org.osate.organization.services;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import java.util.List;

import org.eclipse.xtext.*;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.service.AbstractElementFinder.*;

import org.osate.alisa.common.services.CommonGrammarAccess;

@Singleton
public class OrganizationGrammarAccess extends AbstractGrammarElementFinder {
	
	
	public class OrganizationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Organization");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cOrganizationKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Assignment cStakeholderAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cStakeholderStakeholderParserRuleCall_2_0 = (RuleCall)cStakeholderAssignment_2.eContents().get(0);
		
		//Organization:
		//	"organization" name=ID stakeholder+=Stakeholder+;
		public ParserRule getRule() { return rule; }

		//"organization" name=ID stakeholder+=Stakeholder+
		public Group getGroup() { return cGroup; }

		//"organization"
		public Keyword getOrganizationKeyword_0() { return cOrganizationKeyword_0; }

		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }

		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }

		//stakeholder+=Stakeholder+
		public Assignment getStakeholderAssignment_2() { return cStakeholderAssignment_2; }

		//Stakeholder
		public RuleCall getStakeholderStakeholderParserRuleCall_2_0() { return cStakeholderStakeholderParserRuleCall_2_0; }
	}

	public class StakeholderElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Stakeholder");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cStakeholderKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cLeftSquareBracketKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final UnorderedGroup cUnorderedGroup_2_1 = (UnorderedGroup)cGroup_2.eContents().get(1);
		private final Group cGroup_2_1_0 = (Group)cUnorderedGroup_2_1.eContents().get(0);
		private final Keyword cTitleKeyword_2_1_0_0 = (Keyword)cGroup_2_1_0.eContents().get(0);
		private final Assignment cTitleAssignment_2_1_0_1 = (Assignment)cGroup_2_1_0.eContents().get(1);
		private final RuleCall cTitleValueStringParserRuleCall_2_1_0_1_0 = (RuleCall)cTitleAssignment_2_1_0_1.eContents().get(0);
		private final Group cGroup_2_1_1 = (Group)cUnorderedGroup_2_1.eContents().get(1);
		private final Keyword cDescriptionKeyword_2_1_1_0 = (Keyword)cGroup_2_1_1.eContents().get(0);
		private final Assignment cDescriptionAssignment_2_1_1_1 = (Assignment)cGroup_2_1_1.eContents().get(1);
		private final RuleCall cDescriptionValueStringParserRuleCall_2_1_1_1_0 = (RuleCall)cDescriptionAssignment_2_1_1_1.eContents().get(0);
		private final Group cGroup_2_1_2 = (Group)cUnorderedGroup_2_1.eContents().get(2);
		private final Keyword cRoleKeyword_2_1_2_0 = (Keyword)cGroup_2_1_2.eContents().get(0);
		private final Assignment cRoleAssignment_2_1_2_1 = (Assignment)cGroup_2_1_2.eContents().get(1);
		private final RuleCall cRoleValueStringParserRuleCall_2_1_2_1_0 = (RuleCall)cRoleAssignment_2_1_2_1.eContents().get(0);
		private final Group cGroup_2_1_3 = (Group)cUnorderedGroup_2_1.eContents().get(3);
		private final Keyword cEmailKeyword_2_1_3_0 = (Keyword)cGroup_2_1_3.eContents().get(0);
		private final Assignment cEmailAssignment_2_1_3_1 = (Assignment)cGroup_2_1_3.eContents().get(1);
		private final RuleCall cEmailValueStringParserRuleCall_2_1_3_1_0 = (RuleCall)cEmailAssignment_2_1_3_1.eContents().get(0);
		private final Group cGroup_2_1_4 = (Group)cUnorderedGroup_2_1.eContents().get(4);
		private final Keyword cPhoneKeyword_2_1_4_0 = (Keyword)cGroup_2_1_4.eContents().get(0);
		private final Assignment cPhoneAssignment_2_1_4_1 = (Assignment)cGroup_2_1_4.eContents().get(1);
		private final RuleCall cPhoneValueStringParserRuleCall_2_1_4_1_0 = (RuleCall)cPhoneAssignment_2_1_4_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_2_2 = (Keyword)cGroup_2.eContents().get(2);
		
		/// *
		// * Stakeholder
		// * / Stakeholder:
		//	"stakeholder" name=ID ("[" (("title" title=ValueString)? & ("description" description=ValueString)? & ("role"
		//	role=ValueString)? & ("email" email=ValueString)? & ("phone" phone=ValueString)?) "]")?;
		public ParserRule getRule() { return rule; }

		//"stakeholder" name=ID ("[" (("title" title=ValueString)? & ("description" description=ValueString)? & ("role"
		//role=ValueString)? & ("email" email=ValueString)? & ("phone" phone=ValueString)?) "]")?
		public Group getGroup() { return cGroup; }

		//"stakeholder"
		public Keyword getStakeholderKeyword_0() { return cStakeholderKeyword_0; }

		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }

		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }

		//("[" (("title" title=ValueString)? & ("description" description=ValueString)? & ("role" role=ValueString)? & ("email"
		//email=ValueString)? & ("phone" phone=ValueString)?) "]")?
		public Group getGroup_2() { return cGroup_2; }

		//"["
		public Keyword getLeftSquareBracketKeyword_2_0() { return cLeftSquareBracketKeyword_2_0; }

		//("title" title=ValueString)? & ("description" description=ValueString)? & ("role" role=ValueString)? & ("email"
		//email=ValueString)? & ("phone" phone=ValueString)?
		public UnorderedGroup getUnorderedGroup_2_1() { return cUnorderedGroup_2_1; }

		//("title" title=ValueString)?
		public Group getGroup_2_1_0() { return cGroup_2_1_0; }

		//"title"
		public Keyword getTitleKeyword_2_1_0_0() { return cTitleKeyword_2_1_0_0; }

		//title=ValueString
		public Assignment getTitleAssignment_2_1_0_1() { return cTitleAssignment_2_1_0_1; }

		//ValueString
		public RuleCall getTitleValueStringParserRuleCall_2_1_0_1_0() { return cTitleValueStringParserRuleCall_2_1_0_1_0; }

		//("description" description=ValueString)?
		public Group getGroup_2_1_1() { return cGroup_2_1_1; }

		//"description"
		public Keyword getDescriptionKeyword_2_1_1_0() { return cDescriptionKeyword_2_1_1_0; }

		//description=ValueString
		public Assignment getDescriptionAssignment_2_1_1_1() { return cDescriptionAssignment_2_1_1_1; }

		//ValueString
		public RuleCall getDescriptionValueStringParserRuleCall_2_1_1_1_0() { return cDescriptionValueStringParserRuleCall_2_1_1_1_0; }

		//("role" role=ValueString)?
		public Group getGroup_2_1_2() { return cGroup_2_1_2; }

		//"role"
		public Keyword getRoleKeyword_2_1_2_0() { return cRoleKeyword_2_1_2_0; }

		//role=ValueString
		public Assignment getRoleAssignment_2_1_2_1() { return cRoleAssignment_2_1_2_1; }

		//ValueString
		public RuleCall getRoleValueStringParserRuleCall_2_1_2_1_0() { return cRoleValueStringParserRuleCall_2_1_2_1_0; }

		//("email" email=ValueString)?
		public Group getGroup_2_1_3() { return cGroup_2_1_3; }

		//"email"
		public Keyword getEmailKeyword_2_1_3_0() { return cEmailKeyword_2_1_3_0; }

		//email=ValueString
		public Assignment getEmailAssignment_2_1_3_1() { return cEmailAssignment_2_1_3_1; }

		//ValueString
		public RuleCall getEmailValueStringParserRuleCall_2_1_3_1_0() { return cEmailValueStringParserRuleCall_2_1_3_1_0; }

		//("phone" phone=ValueString)?
		public Group getGroup_2_1_4() { return cGroup_2_1_4; }

		//"phone"
		public Keyword getPhoneKeyword_2_1_4_0() { return cPhoneKeyword_2_1_4_0; }

		//phone=ValueString
		public Assignment getPhoneAssignment_2_1_4_1() { return cPhoneAssignment_2_1_4_1; }

		//ValueString
		public RuleCall getPhoneValueStringParserRuleCall_2_1_4_1_0() { return cPhoneValueStringParserRuleCall_2_1_4_1_0; }

		//"]"
		public Keyword getRightSquareBracketKeyword_2_2() { return cRightSquareBracketKeyword_2_2; }
	}
	
	
	private final OrganizationElements pOrganization;
	private final StakeholderElements pStakeholder;
	
	private final Grammar grammar;

	private final CommonGrammarAccess gaCommon;

	@Inject
	public OrganizationGrammarAccess(GrammarProvider grammarProvider,
		CommonGrammarAccess gaCommon) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaCommon = gaCommon;
		this.pOrganization = new OrganizationElements();
		this.pStakeholder = new StakeholderElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.osate.organization.Organization".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	
	public Grammar getGrammar() {
		return grammar;
	}
	

	public CommonGrammarAccess getCommonGrammarAccess() {
		return gaCommon;
	}

	
	//Organization:
	//	"organization" name=ID stakeholder+=Stakeholder+;
	public OrganizationElements getOrganizationAccess() {
		return pOrganization;
	}
	
	public ParserRule getOrganizationRule() {
		return getOrganizationAccess().getRule();
	}

	/// *
	// * Stakeholder
	// * / Stakeholder:
	//	"stakeholder" name=ID ("[" (("title" title=ValueString)? & ("description" description=ValueString)? & ("role"
	//	role=ValueString)? & ("email" email=ValueString)? & ("phone" phone=ValueString)?) "]")?;
	public StakeholderElements getStakeholderAccess() {
		return pStakeholder;
	}
	
	public ParserRule getStakeholderRule() {
		return getStakeholderAccess().getRule();
	}

	//Model:
	//	content=Description;
	public CommonGrammarAccess.ModelElements getModelAccess() {
		return gaCommon.getModelAccess();
	}
	
	public ParserRule getModelRule() {
		return getModelAccess().getRule();
	}

	//Description:
	//	description+=DescriptionElement+;
	public CommonGrammarAccess.DescriptionElements getDescriptionAccess() {
		return gaCommon.getDescriptionAccess();
	}
	
	public ParserRule getDescriptionRule() {
		return getDescriptionAccess().getRule();
	}

	//DescriptionElement:
	//	text=STRING | ref=[ecore::EObject];
	public CommonGrammarAccess.DescriptionElementElements getDescriptionElementAccess() {
		return gaCommon.getDescriptionElementAccess();
	}
	
	public ParserRule getDescriptionElementRule() {
		return getDescriptionElementAccess().getRule();
	}

	//ReferencePath:
	//	ref=[ecore::EObject] ("." subpath=ReferencePath);
	public CommonGrammarAccess.ReferencePathElements getReferencePathAccess() {
		return gaCommon.getReferencePathAccess();
	}
	
	public ParserRule getReferencePathRule() {
		return getReferencePathAccess().getRule();
	}

	//Import:
	//	"import" importedNamespace=QualifiedNameWithWildcard;
	public CommonGrammarAccess.ImportElements getImportAccess() {
		return gaCommon.getImportAccess();
	}
	
	public ParserRule getImportRule() {
		return getImportAccess().getRule();
	}

	//ValueString: // remove quotes from string in ValueConverter 
	//	STRING;
	public CommonGrammarAccess.ValueStringElements getValueStringAccess() {
		return gaCommon.getValueStringAccess();
	}
	
	public ParserRule getValueStringRule() {
		return getValueStringAccess().getRule();
	}

	//// dotted path as relative reference
	//QualifiedName:
	//	ID ("." ID)*;
	public CommonGrammarAccess.QualifiedNameElements getQualifiedNameAccess() {
		return gaCommon.getQualifiedNameAccess();
	}
	
	public ParserRule getQualifiedNameRule() {
		return getQualifiedNameAccess().getRule();
	}

	//// qualified named with wildcard
	//QualifiedNameWithWildcard:
	//	QualifiedName ".*"?;
	public CommonGrammarAccess.QualifiedNameWithWildcardElements getQualifiedNameWithWildcardAccess() {
		return gaCommon.getQualifiedNameWithWildcardAccess();
	}
	
	public ParserRule getQualifiedNameWithWildcardRule() {
		return getQualifiedNameWithWildcardAccess().getRule();
	}

	//// Qualified classifier reference
	//AadlClassifierReference:
	//	(ID "::")+ ID ("." ID)?;
	public CommonGrammarAccess.AadlClassifierReferenceElements getAadlClassifierReferenceAccess() {
		return gaCommon.getAadlClassifierReferenceAccess();
	}
	
	public ParserRule getAadlClassifierReferenceRule() {
		return getAadlClassifierReferenceAccess().getRule();
	}

	//// Category reference. Currently it is only a single ID
	//CatRef: //('.' ID)?
	//	ID;
	public CommonGrammarAccess.CatRefElements getCatRefAccess() {
		return gaCommon.getCatRefAccess();
	}
	
	public ParserRule getCatRefRule() {
		return getCatRefAccess().getRule();
	}

	//terminal ID:
	//	"^"? ("a".."z" | "A".."Z" | "_") ("a".."z" | "A".."Z" | "_" | "0".."9")*;
	public TerminalRule getIDRule() {
		return gaCommon.getIDRule();
	} 

	//terminal INT returns ecore::EInt:
	//	"0".."9"+;
	public TerminalRule getINTRule() {
		return gaCommon.getINTRule();
	} 

	//terminal STRING:
	//	"\"" ("\\" . / * 'b'|'t'|'n'|'f'|'r'|'u'|'"'|"'"|'\\' * / | !("\\" | "\""))* "\"" | "\'" ("\\" .
	//	/ * 'b'|'t'|'n'|'f'|'r'|'u'|'"'|"'"|'\\' * / | !("\\" | "\'"))* "\'";
	public TerminalRule getSTRINGRule() {
		return gaCommon.getSTRINGRule();
	} 

	//terminal ML_COMMENT:
	//	"/ *"->"* /";
	public TerminalRule getML_COMMENTRule() {
		return gaCommon.getML_COMMENTRule();
	} 

	//terminal SL_COMMENT:
	//	"//" !("\n" | "\r")* ("\r"? "\n")?;
	public TerminalRule getSL_COMMENTRule() {
		return gaCommon.getSL_COMMENTRule();
	} 

	//terminal WS:
	//	(" " | "\t" | "\r" | "\n")+;
	public TerminalRule getWSRule() {
		return gaCommon.getWSRule();
	} 

	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaCommon.getANY_OTHERRule();
	} 
}