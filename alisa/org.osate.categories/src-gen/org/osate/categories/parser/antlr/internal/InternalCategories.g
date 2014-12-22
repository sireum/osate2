/*
* generated by Xtext
*/
grammar InternalCategories;

options {
	superClass=AbstractInternalAntlrParser;
	
}

@lexer::header {
package org.osate.categories.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.osate.categories.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.osate.categories.services.CategoriesGrammarAccess;

}

@parser::members {

 	private CategoriesGrammarAccess grammarAccess;
 	
    public InternalCategoriesParser(TokenStream input, CategoriesGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }
    
    @Override
    protected String getFirstRuleName() {
    	return "Categories";	
   	}
   	
   	@Override
   	protected CategoriesGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}
}

@rulecatch { 
    catch (RecognitionException re) { 
        recover(input,re); 
        appendSkippedTokens();
    } 
}




// Entry rule entryRuleCategories
entryRuleCategories returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getCategoriesRule()); }
	 iv_ruleCategories=ruleCategories 
	 { $current=$iv_ruleCategories.current; } 
	 EOF 
;

// Rule Categories
ruleCategories returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getCategoriesAccess().getRequirementCategoriesParserRuleCall_0()); 
    }
    this_RequirementCategories_0=ruleRequirementCategories
    { 
        $current = $this_RequirementCategories_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getCategoriesAccess().getHazardCategoriesParserRuleCall_1()); 
    }
    this_HazardCategories_1=ruleHazardCategories
    { 
        $current = $this_HazardCategories_1.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getCategoriesAccess().getVerificationCategoriesParserRuleCall_2()); 
    }
    this_VerificationCategories_2=ruleVerificationCategories
    { 
        $current = $this_VerificationCategories_2.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleRequirementCategories
entryRuleRequirementCategories returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getRequirementCategoriesRule()); }
	 iv_ruleRequirementCategories=ruleRequirementCategories 
	 { $current=$iv_ruleRequirementCategories.current; } 
	 EOF 
;

// Rule RequirementCategories
ruleRequirementCategories returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='requirement' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getRequirementCategoriesAccess().getRequirementKeyword_0());
    }
	otherlv_1='categories' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getRequirementCategoriesAccess().getCategoriesKeyword_1());
    }
	otherlv_2='[' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getRequirementCategoriesAccess().getLeftSquareBracketKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getRequirementCategoriesAccess().getCategoryRequirementCategoryParserRuleCall_3_0()); 
	    }
		lv_category_3_0=ruleRequirementCategory		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getRequirementCategoriesRule());
	        }
       		add(
       			$current, 
       			"category",
        		lv_category_3_0, 
        		"RequirementCategory");
	        afterParserOrEnumRuleCall();
	    }

)
)+	otherlv_4=']' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getRequirementCategoriesAccess().getRightSquareBracketKeyword_4());
    }
)
;





// Entry rule entryRuleHazardCategories
entryRuleHazardCategories returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getHazardCategoriesRule()); }
	 iv_ruleHazardCategories=ruleHazardCategories 
	 { $current=$iv_ruleHazardCategories.current; } 
	 EOF 
;

// Rule HazardCategories
ruleHazardCategories returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='hazard' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getHazardCategoriesAccess().getHazardKeyword_0());
    }
	otherlv_1='categories' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getHazardCategoriesAccess().getCategoriesKeyword_1());
    }
	otherlv_2='[' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getHazardCategoriesAccess().getLeftSquareBracketKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getHazardCategoriesAccess().getCategoryHazardCategoryParserRuleCall_3_0()); 
	    }
		lv_category_3_0=ruleHazardCategory		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getHazardCategoriesRule());
	        }
       		add(
       			$current, 
       			"category",
        		lv_category_3_0, 
        		"HazardCategory");
	        afterParserOrEnumRuleCall();
	    }

)
)+	otherlv_4=']' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getHazardCategoriesAccess().getRightSquareBracketKeyword_4());
    }
)
;





// Entry rule entryRuleVerificationCategories
entryRuleVerificationCategories returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getVerificationCategoriesRule()); }
	 iv_ruleVerificationCategories=ruleVerificationCategories 
	 { $current=$iv_ruleVerificationCategories.current; } 
	 EOF 
;

// Rule VerificationCategories
ruleVerificationCategories returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='verification' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getVerificationCategoriesAccess().getVerificationKeyword_0());
    }
	otherlv_1='categories' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getVerificationCategoriesAccess().getCategoriesKeyword_1());
    }
	otherlv_2='[' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getVerificationCategoriesAccess().getLeftSquareBracketKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getVerificationCategoriesAccess().getCategoryVerificationCategoryParserRuleCall_3_0()); 
	    }
		lv_category_3_0=ruleVerificationCategory		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getVerificationCategoriesRule());
	        }
       		add(
       			$current, 
       			"category",
        		lv_category_3_0, 
        		"VerificationCategory");
	        afterParserOrEnumRuleCall();
	    }

)
)+	otherlv_4=']' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getVerificationCategoriesAccess().getRightSquareBracketKeyword_4());
    }
)
;







// Entry rule entryRuleRequirementCategory
entryRuleRequirementCategory returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getRequirementCategoryRule()); }
	 iv_ruleRequirementCategory=ruleRequirementCategory 
	 { $current=$iv_ruleRequirementCategory.current; } 
	 EOF 
;

// Rule RequirementCategory
ruleRequirementCategory returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='category' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getRequirementCategoryAccess().getCategoryKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getRequirementCategoryAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getRequirementCategoryRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)(	otherlv_2='extends' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getRequirementCategoryAccess().getExtendsKeyword_2_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getRequirementCategoryRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getRequirementCategoryAccess().getExtendsRequirementCategoryCrossReference_2_1_0()); 
	    }
		ruleCatRef		{ 
	        afterParserOrEnumRuleCall();
	    }

)
))?)
;





// Entry rule entryRuleHazardCategory
entryRuleHazardCategory returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getHazardCategoryRule()); }
	 iv_ruleHazardCategory=ruleHazardCategory 
	 { $current=$iv_ruleHazardCategory.current; } 
	 EOF 
;

// Rule HazardCategory
ruleHazardCategory returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='category' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getHazardCategoryAccess().getCategoryKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getHazardCategoryAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getHazardCategoryRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)(	otherlv_2='extends' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getHazardCategoryAccess().getExtendsKeyword_2_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getHazardCategoryRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getHazardCategoryAccess().getExtendsHazardCategoryCrossReference_2_1_0()); 
	    }
		ruleCatRef		{ 
	        afterParserOrEnumRuleCall();
	    }

)
))?)
;





// Entry rule entryRuleVerificationCategory
entryRuleVerificationCategory returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getVerificationCategoryRule()); }
	 iv_ruleVerificationCategory=ruleVerificationCategory 
	 { $current=$iv_ruleVerificationCategory.current; } 
	 EOF 
;

// Rule VerificationCategory
ruleVerificationCategory returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='category' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getVerificationCategoryAccess().getCategoryKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getVerificationCategoryAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getVerificationCategoryRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)(	otherlv_2='extends' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getVerificationCategoryAccess().getExtendsKeyword_2_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getVerificationCategoryRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getVerificationCategoryAccess().getExtendsVerificationCategoryCrossReference_2_1_0()); 
	    }
		ruleCatRef		{ 
	        afterParserOrEnumRuleCall();
	    }

)
))?)
;







// Entry rule entryRuleDescription
entryRuleDescription returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getDescriptionRule()); }
	 iv_ruleDescription=ruleDescription 
	 { $current=$iv_ruleDescription.current; } 
	 EOF 
;

// Rule Description
ruleDescription returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		{ 
	        newCompositeNode(grammarAccess.getDescriptionAccess().getDescriptionDescriptionElementParserRuleCall_0()); 
	    }
		lv_description_0_0=ruleDescriptionElement		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getDescriptionRule());
	        }
       		add(
       			$current, 
       			"description",
        		lv_description_0_0, 
        		"DescriptionElement");
	        afterParserOrEnumRuleCall();
	    }

)
)+
;





// Entry rule entryRuleDescriptionElement
entryRuleDescriptionElement returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getDescriptionElementRule()); }
	 iv_ruleDescriptionElement=ruleDescriptionElement 
	 { $current=$iv_ruleDescriptionElement.current; } 
	 EOF 
;

// Rule DescriptionElement
ruleDescriptionElement returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		lv_text_0_0=RULE_STRING
		{
			newLeafNode(lv_text_0_0, grammarAccess.getDescriptionElementAccess().getTextSTRINGTerminalRuleCall_0_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getDescriptionElementRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"text",
        		lv_text_0_0, 
        		"STRING");
	    }

)
)
    |(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getDescriptionElementRule());
	        }
        }
	otherlv_1=RULE_ID
	{
		newLeafNode(otherlv_1, grammarAccess.getDescriptionElementAccess().getRefEObjectCrossReference_1_0()); 
	}

)
))
;





// Entry rule entryRuleReferencePath
entryRuleReferencePath returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getReferencePathRule()); }
	 iv_ruleReferencePath=ruleReferencePath 
	 { $current=$iv_ruleReferencePath.current; } 
	 EOF 
;

// Rule ReferencePath
ruleReferencePath returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getReferencePathRule());
	        }
        }
	otherlv_0=RULE_ID
	{
		newLeafNode(otherlv_0, grammarAccess.getReferencePathAccess().getRefEObjectCrossReference_0_0()); 
	}

)
)(	otherlv_1='.' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getReferencePathAccess().getFullStopKeyword_1_0());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getReferencePathAccess().getSubpathReferencePathParserRuleCall_1_1_0()); 
	    }
		lv_subpath_2_0=ruleReferencePath		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getReferencePathRule());
	        }
       		set(
       			$current, 
       			"subpath",
        		lv_subpath_2_0, 
        		"ReferencePath");
	        afterParserOrEnumRuleCall();
	    }

)
)))
;









// Entry rule entryRuleQualifiedName
entryRuleQualifiedName returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getQualifiedNameRule()); } 
	 iv_ruleQualifiedName=ruleQualifiedName 
	 { $current=$iv_ruleQualifiedName.current.getText(); }  
	 EOF 
;

// Rule QualifiedName
ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(    this_ID_0=RULE_ID    {
		$current.merge(this_ID_0);
    }

    { 
    newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
    }
(
	kw='.' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
    }
    this_ID_2=RULE_ID    {
		$current.merge(this_ID_2);
    }

    { 
    newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
    }
)*)
    ;





// Entry rule entryRuleQualifiedNameWithWildcard
entryRuleQualifiedNameWithWildcard returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getQualifiedNameWithWildcardRule()); } 
	 iv_ruleQualifiedNameWithWildcard=ruleQualifiedNameWithWildcard 
	 { $current=$iv_ruleQualifiedNameWithWildcard.current.getText(); }  
	 EOF 
;

// Rule QualifiedNameWithWildcard
ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0()); 
    }
    this_QualifiedName_0=ruleQualifiedName    {
		$current.merge(this_QualifiedName_0);
    }

    { 
        afterParserOrEnumRuleCall();
    }
(
	kw='.*' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopAsteriskKeyword_1()); 
    }
)?)
    ;







// Entry rule entryRuleCatRef
entryRuleCatRef returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getCatRefRule()); } 
	 iv_ruleCatRef=ruleCatRef 
	 { $current=$iv_ruleCatRef.current.getText(); }  
	 EOF 
;

// Rule CatRef
ruleCatRef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
    this_ID_0=RULE_ID    {
		$current.merge(this_ID_0);
    }

    { 
    newLeafNode(this_ID_0, grammarAccess.getCatRefAccess().getIDTerminalRuleCall()); 
    }

    ;





RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;

