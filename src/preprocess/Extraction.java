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
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Wai Nwe Tun
 */
public class Extraction {
    private StringBuilder selected;
    private StringBuilder adj;
    private StringBuilder adv;
    private StringBuilder verb;

    public StringBuilder getAdj() {
        return adj;
    }

    public StringBuilder getAdv() {
        return adv;
    }

    public StringBuilder getVerb() {
        return verb;
    }
    
    public Extraction(){
        selected = new StringBuilder();
        adj = new StringBuilder();
        adv = new StringBuilder();
        verb = new StringBuilder();
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
                    boolean flag = false;
                    char charFlag = ' ';
                    String tag = tSentence.get(j).tag();
                    /*
                    if(tag.equals("NN") || tag.equals("NNS")){	// noun
                        flag = true;

                    }
                    */
                    if(tag.equals("VBP") || tag.equals("VBZ") || tag.equals("VB") || tag.equals("VBD") || tag.equals("VBN")){	//verb
                        flag = true;
                        charFlag = 'v';
                    }
                    if(tag.equals("JJ") || tag.equals("JJR") || tag.equals("JJS")){	//adj
                       flag = true;
                       charFlag = 'j';
                     }
                    if(tag.equals("RB") || tag.equals("RBR") || tag.equals("RBS")){		//adv
                       flag = true;
                       charFlag = 'a';
                     }
                    if(tag.equals("DT")){		// no (negation) 's tag is DT.
                       if(tSentence.get(j).word().equals("no") || tSentence.get(j).word().equals("neither")){
                           flag = true;
                       }
                    }
                    String temp = tSentence.get(j).word();
			    	  
                    if(temp.length()<3){
                        flag = false;
                    }

                    if(flag){		
                          negated = handleNegation(temp, negated);		//calling handlingNegation() 				    	  
                          if(negated){				    		  
                              temp = "!" + temp;
                          }
                          //selected = selected + temp + " ";
                          switch(charFlag){
                              case 'v'  : verb.append(temp + " "); 
                                  break;
                              case 'j'  : adj.append(temp + " ");
                                  break;
                              case 'a'  : adv.append(temp + " ");
                                  break;
                          }
                          selected.append(temp + " ");
                    }	    	  
                }   			    	  
            }
            PrintWriter pw = new PrintWriter(new File("review.txt"));
            pw.write(selected.toString());
            pw.close();
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
	if(word.equals("no") || word.equals("not") || word.equals("never") || word.equals("n't") || word.equals("neither") || word.equals("nothing") || word.equals("nothin")){
		negated = !negated;
	}				
	return negated;		
    }
    
    
     public String getOutput(){
        return this.selected.toString();
    }
}
