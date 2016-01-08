/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package preprocess;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author Wai Nwe Tun
 */
public class NegativeHandling {
    private StringBuilder selected;
    
    public NegativeHandling(){
        selected = new StringBuilder();
    }
    
    public void posTagging(){
        MaxentTagger tagger = new MaxentTagger("lib/english-left3words-distsim.tagger");
	List<List<HasWord>> sentences;
	try {				
            sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(new File("review.txt"))));
            
    		    //to access single sentence for tagging
            for (List<HasWord> sentence : sentences) {
                List<TaggedWord> tSentence = tagger.tagSentence(sentence);		      
                boolean negated = false;
			      
			      // to parse each word for extraction
                for(int j = 0; j<tSentence.size(); j++){		    	  
                    String temp = tSentence.get(j).word();	  
				    	  
                    negated = handleNegation(temp, negated);		//calling handlingNegation() 
                    temp = tSentence.get(j).toString();
                    if(negated){
	    		  temp = "!" + temp;
                    }			                     
                    selected.append(temp + ",");
                }		      
	   }		   
        }	
        catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
            System.out.println(e.getMessage());
	}		    
    }
 
    public String checkSpecialCharacter(String string, String character){
	//System.out.println(string);
    	if(string.indexOf(character)==0){
		string = string.substring(1);
	}
	else if(string.indexOf(character)>0){
		 String[] tempArr = string.split(character);
		 string = "";
		 for(String tempStr: tempArr){
			  string += tempStr;			  
		 }
	}
	return string;
    }
	
	
	/*
	 * Negative Handling
	 * word, orginal negated value (true|false)
	 * the fact of negative will be considered based on that it holds another negation
	 * if there are even number of negation, it'd be reverted to 'not negated'.
	 * it'll append '!' at the front of the words following negative word
	 */
    public boolean handleNegation(String word, boolean negated){		
	if(word.equals("no") || word.equals("not") || word.equals("never") || word.equals("neither") || word.equals("nothing") || word.equals("nothin") || word.equals("n't")|| word.equals("rarely") || word.equals("lack") || word.equals("lacks") || word.equals("without") || word.equals("less") || word.equals("hardly") || word.equals("barely") || word.equals("no longer")){
		negated = !negated;
	}				
	return negated;		
    }
	
    public String getOutput(){
        return this.selected.toString();
    }
}
