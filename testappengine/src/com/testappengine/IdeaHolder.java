package com.testappengine;

import com.google.api.client.util.DateTime;
import com.testappengine.ideaendpoint.model.Idea;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IdeaHolder {

	Idea newIdea;
	
	public IdeaHolder(Idea idea) {
		newIdea = idea;
	}
	
	public IdeaHolder(String idea, String posterName, String category) {
		newIdea = new Idea();
		newIdea.setIdea(idea);
		newIdea.setPoster(posterName);
		newIdea.setCategory(category);
		newIdea.setPostDate(new DateTime(new Date()));
		newIdea.setUpvotes(0);
		newIdea.setDownvotes(0);
		newIdea.setPromotions(0);
		newIdea.setJunk(0);
    	newIdea.setNumOfComments(0);
    	newIdea.setComments(new ArrayList<String>());
    	newIdea.setComUsernames(new ArrayList<String>());
    	newIdea.setComUpvotes(new ArrayList<Integer>());
    	newIdea.setComDownvotes(new ArrayList<Integer>());
    	newIdea.setComPromotions(new ArrayList<Integer>());
    	newIdea.setComJunks(new ArrayList<Integer>());
    	newIdea.setComToAdd("");
    	newIdea.setComUserToAdd("");
    	newIdea.setComUpToAdd(0);
    	newIdea.setComDownToAdd(0);
    	newIdea.setComPromToAdd(0);
    	newIdea.setComJunkToAdd(0);
    	newIdea.setComToAddTo(0);
	}
	
	public void cleanUp() {
    	newIdea.setComToAdd("");
    	newIdea.setComUserToAdd("");
    	newIdea.setComUpToAdd(0);
    	newIdea.setComDownToAdd(0);
    	newIdea.setComPromToAdd(0);
    	newIdea.setComJunkToAdd(0);
    	newIdea.setComToAddTo(0);
	}
	
	public Idea getIdea() {
		return newIdea;
	}
	public void setIdea(Idea newIdea) {
		this.newIdea = newIdea;
	}

    public void addToUpvotes(int add) {
    	newIdea.setUpvotes(newIdea.getUpvotes() + add);
    	newIdea.setUpvotesToAdd(add);
    }
    public void addToDownvotes(int add) {
    	newIdea.setDownvotes(newIdea.getDownvotes() + add);
    	newIdea.setDownvotesToAdd(add);
    }
    public void addToPromotions(int add) {
    	newIdea.setPromotions(newIdea.getPromotions() + add);
    	newIdea.setPromotionsToAdd(add);
    }
    public void addToJunk(int add) {
    	newIdea.setJunk(newIdea.getJunk() + add);
    	newIdea.setJunkToAdd(add);
    }
    public void addToComUpvotes(int add, int pos) {
    	newIdea.setComUpToAdd(add);
    	newIdea.setComToAddTo(pos);
    }
    public void addToComDownvotes(int add, int pos) {
    	newIdea.setComDownToAdd(add);
    	newIdea.setComToAddTo(pos);
    }
    public void addToComPromotions(int add, int pos) {
    	newIdea.setComPromToAdd(add);
    	newIdea.setComToAddTo(pos);
    }
    public void addToComJunk(int add, int pos) {
    	newIdea.setComJunkToAdd(add);
    	newIdea.setComToAddTo(pos);
    }
    public void addToNumOfComments(int add) {
    	newIdea.setNumOfComments(newIdea.getNumOfComments() + add);
    }
    
    public void addComment(String com) {
    	List<String> tempArr = newIdea.getComments();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	tempArr.add(com);
    	newIdea.setComments(tempArr);
    	newIdea.setComToAdd(com);
    	newIdea.setComToAddTo(-1);
    }
    public void insertComment(int pos, String com) {
    	List<String> tempArr = newIdea.getComments();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	tempArr.add(pos, com);
    	newIdea.setComments(tempArr);
    	newIdea.setComToAdd(com);
    	newIdea.setComToAddTo(pos);
    }
    public void removeComment(int pos) {
    	List<String> tempArr = newIdea.getComments();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	tempArr.remove(pos);
    	newIdea.setComments(tempArr);
    }
    public void removeComment(String com) {
    	List<String> tempArr = newIdea.getComments();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	tempArr.remove(com);
    	newIdea.setComments(tempArr);
    }
    public int getIndexOfCom(String com) {
    	return newIdea.getComments().indexOf(com);
    }
    public ArrayList<Integer> getIndexOfComMult(String com) {
    	List<String> tempArr = newIdea.getComments();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	ArrayList<Integer> indices = new ArrayList<Integer>();
    	for(int i=0; i<tempArr.size(); i++) {
    		if(tempArr.get(i).equals(com)) {
            	indices.add(i);
    		}
    	}
    	return indices;
    }

    public void addComUsername(String comUser) {
    	List<String> tempArr = newIdea.getComUsernames();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	tempArr.add(comUser);
    	newIdea.setComUsernames(tempArr);
    	newIdea.setComUserToAdd(comUser);
    	newIdea.setComToAddTo(-1);
    }
    public void insertComUsername(int pos, String comUser) {
    	List<String> tempArr = newIdea.getComUsernames();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	tempArr.add(pos, comUser);
    	newIdea.setComUsernames(tempArr);
    	newIdea.setComUserToAdd(comUser);
    	newIdea.setComToAddTo(pos);
    }
    public void removeComUsername(int pos) {
    	List<String> tempArr = newIdea.getComUsernames();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	tempArr.remove(pos);
    	newIdea.setComUsernames(tempArr);
    }
    public void removeComUsername(String comUser) {
    	List<String> tempArr = newIdea.getComUsernames();
    	if(tempArr == null)
    		tempArr = new ArrayList<String>();
    	tempArr.remove(comUser);
    	newIdea.setComUsernames(tempArr);
    }
    public int getIndexOfUser(String user) {
    	return newIdea.getComUsernames().indexOf(user);
    }
    public ArrayList<Integer> getIndexOfUserMult(String user) {
    	List<String> tempArr = newIdea.getComUsernames();
    	ArrayList<Integer> indices = new ArrayList<Integer>();
    	for(int i=0; i<tempArr.size(); i++) {
    		if(tempArr.get(i).equals(user)) {
            	indices.add(i);
    		}
    	}
    	return indices;
    }

    public void addComUpvotes(int comUp) {
    	List<Integer> tempArr = newIdea.getComUpvotes();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(comUp);
    	newIdea.setComUpvotes(tempArr);
    	newIdea.setComUpToAdd(comUp);
    	newIdea.setComToAddTo(-1);
    }
    public void insertComUpvotes(int pos, int comUp) {
    	List<Integer> tempArr = newIdea.getComUpvotes();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(pos, comUp);
    	newIdea.setComUpvotes(tempArr);
    	newIdea.setComUpToAdd(comUp);
    	newIdea.setComToAddTo(pos);
    }
    public void removeComUpvotes(int pos) {
    	List<Integer> tempArr = newIdea.getComUpvotes();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.remove(pos);
    	newIdea.setComUpvotes(tempArr);
    }
    public void removeComUpvotesObj(int comUp) {
    	List<Integer> tempArr = newIdea.getComUpvotes();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(comUp);
    	newIdea.setComUpvotes(tempArr);
    }
    
    public void addComDownvotes(int comDown) {
    	List<Integer> tempArr = newIdea.getComDownvotes();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(comDown);
    	newIdea.setComDownvotes(tempArr);
    	newIdea.setComDownToAdd(comDown);
    	newIdea.setComToAddTo(-1);
    }
    public void insertComDownvotes(int pos, int comDown) {
    	List<Integer> tempArr = newIdea.getComDownvotes();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(pos, comDown);
    	newIdea.setComDownvotes(tempArr);
    	newIdea.setComDownToAdd(comDown);
    	newIdea.setComToAddTo(pos);
    }
    public void removeComDownvotes(int pos) {
    	List<Integer> tempArr = newIdea.getComDownvotes();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.remove(pos);
    	newIdea.setComDownvotes(tempArr);
    }
    public void removeComDownvotesObj(int comDown) {
    	List<Integer> tempArr = newIdea.getComDownvotes();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.remove(comDown);
    	newIdea.setComDownvotes(tempArr);
    }

    public void addComPromotions(int comProm) {
    	List<Integer> tempArr = newIdea.getComPromotions();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(comProm);
    	newIdea.setComPromotions(tempArr);
    	newIdea.setComPromToAdd(comProm);
    	newIdea.setComToAddTo(-1);
    }
    public void insertComPromotions(int pos, int comProm) {
    	List<Integer> tempArr = newIdea.getComPromotions();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(pos, comProm);
    	newIdea.setComPromotions(tempArr);
    	newIdea.setComPromToAdd(comProm);
    	newIdea.setComToAddTo(pos);
    }
    public void removeComPromotions(int pos) {
    	List<Integer> tempArr = newIdea.getComPromotions();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.remove(pos);
    	newIdea.setComPromotions(tempArr);
    }
    public void removeComPromotionsObj(int comProm) {
    	List<Integer> tempArr = newIdea.getComPromotions();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.remove(comProm);
    	newIdea.setComPromotions(tempArr);
    }
    
    public void addComJunks(int comJunk) {
    	List<Integer> tempArr = newIdea.getComJunks();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(comJunk);
    	newIdea.setComJunks(tempArr);
    	newIdea.setComJunkToAdd(comJunk);
    	newIdea.setComToAddTo(-1);
    }
    public void insertComJunks(int pos, int comJunk) {
    	List<Integer> tempArr = newIdea.getComJunks();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(pos, comJunk);
    	newIdea.setComJunks(tempArr);
    	newIdea.setComJunkToAdd(comJunk);
    	newIdea.setComToAddTo(pos);
    }
    public void removeComJunks(int pos) {
    	List<Integer> tempArr = newIdea.getComJunks();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.remove(pos);
    	newIdea.setComJunks(tempArr);
    }
    public void removeComJunkObj(int comJunk) {
    	List<Integer> tempArr = newIdea.getComJunks();
    	if(tempArr == null)
    		tempArr = new ArrayList<Integer>();
    	tempArr.add(comJunk);
    	newIdea.setComJunks(tempArr);
    }
    
    
	public void addAComment(String com, String user) {
    	addToNumOfComments(1);
    	addComment(com);
    	addComUsername(user);
    	addComUpvotes(0);
    	addComDownvotes(0);
    	addComPromotions(0);
    	addComJunks(0);
    }
    public void addAComment(String com, String user, int upvotes, int downvotes) {
    	addToNumOfComments(1);
    	addComment(com);
    	addComUsername(user);
    	addComUpvotes(upvotes);
    	addComDownvotes(downvotes);
    	addComPromotions(0);
    	addComJunks(0);
    }
    public void addAComment(String com, String user, int upvotes, int downvotes, int promotions, int junks) {
    	addToNumOfComments(1);
    	addComment(com);
    	addComUsername(user);
    	addComUpvotes(upvotes);
    	addComDownvotes(downvotes);
    	addComPromotions(promotions);
    	addComJunks(junks);
    }
    public void insertAComment(int pos, String com, String user) {
    	addToNumOfComments(1);
    	insertComment(pos, com);
    	insertComUsername(pos, user);
    	insertComUpvotes(pos, 0);
    	insertComDownvotes(pos, 0);
    	insertComPromotions(pos, 0);
    	insertComJunks(pos, 0);
    }
    public void insertAComment(int pos, String com, String user, int upvotes, int downvotes) {
    	addToNumOfComments(1);
    	insertComment(pos, com);
    	insertComUsername(pos, user);
    	insertComUpvotes(pos, upvotes);
    	insertComDownvotes(pos, downvotes);
    	insertComPromotions(pos, 0);
    	insertComJunks(pos, 0);
    }
    public void insertAComment(int pos, String com, String user, int upvotes, int downvotes, int promotions, int junks) {
    	addToNumOfComments(1);
    	insertComment(pos, com);
    	insertComUsername(pos, user);
    	insertComUpvotes(pos, upvotes);
    	insertComDownvotes(pos, downvotes);
    	insertComPromotions(pos, promotions);
    	insertComJunks(pos, junks);
    }
    public void removeAComment(int pos) {
    	addToNumOfComments(-1);
    	removeComment(pos);
    	removeComUsername(pos);
    	removeComUpvotes(pos);
    	removeComDownvotes(pos);
    	removeComPromotions(pos);
    	removeComJunks(pos);
    }
    public void removeACommentOf(String com) {
    	int index = getIndexOfCom(com);
    	if(index != -1) {
    		addToNumOfComments(-1);
	    	removeComment(index);
	    	removeComUsername(index);
	    	removeComUpvotes(index);
	    	removeComDownvotes(index);
	    	removeComPromotions(index);
	    	removeComJunks(index);
    	}
    }
    public void removeAllCommentsOf(String com) {
    	ArrayList<Integer> indices = getIndexOfComMult(com);
    	addToNumOfComments(-indices.size());
    	for(int i=0; i<indices.size(); i++) {
	    	removeComment(indices.get(i));
	    	removeComUsername(indices.get(i));
	    	removeComUpvotes(indices.get(i));
	    	removeComDownvotes(indices.get(i));
	    	removeComPromotions(indices.get(i));
	    	removeComJunks(indices.get(i));
    	}
    }
    public void removeACommentBy(String user) {
    	int index = getIndexOfUser(user);
    	if(index != -1) {
    		addToNumOfComments(-1);
	    	removeComment(index);
	    	removeComUsername(index);
	    	removeComUpvotes(index);
	    	removeComDownvotes(index);
	    	removeComPromotions(index);
	    	removeComJunks(index);
    	}
    }
    public void removeAllCommentsBy(String user) {
    	ArrayList<Integer> indices = getIndexOfUserMult(user);
    	addToNumOfComments(-indices.size());
    	for(int i=0; i<indices.size(); i++) {
	    	removeComment(indices.get(i));
	    	removeComUsername(indices.get(i));
	    	removeComUpvotes(indices.get(i));
	    	removeComDownvotes(indices.get(i));
	    	removeComPromotions(indices.get(i));
	    	removeComJunks(indices.get(i));
    	}
    }
	
}
