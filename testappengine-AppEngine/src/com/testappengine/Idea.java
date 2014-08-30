/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.testappengine;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="course_seq")
	@SequenceGenerator(
			name="course_seq",
			sequenceName="course_sequence",
			allocationSize=100000
		)
    private Long key;
    private String idea;
    private String posterName;
    private Date postDate;
    private String category;
    private int upvotes;
    private int upvotesToAdd;
    private int downvotes;
    private int downvotesToAdd;
    private int promotions;
    private int promotionsToAdd;
    private int junk;
    private int junkToAdd;
    private int numOfComments;
    private ArrayList<String> comments;
    private String comToAdd;
    private int comToAddTo;
    private ArrayList<String> commentUsernames;
    private String comUserToAdd;
    private ArrayList<Integer> commentUpvotes;
    private int comUpToAdd;
    private ArrayList<Integer> commentDownvotes;
    private int comDownToAdd;
    private ArrayList<Integer> commentPromotions;
    private int comPromToAdd;
    private ArrayList<Integer> commentJunks;
    private int comJunkToAdd;
    
    public Long getKey() {
        return key;
    }

    public String getIdea() {
        return idea;
    }
    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getPoster() {
        return posterName;
    }
    public void setPoster(String posterName) {
        this.posterName = posterName;
    }

    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date date) {
        postDate = date;
    }
    
    public int getUpvotes() {
    	return upvotes;
    }
    public void setUpvotes(int votes) {
    	upvotes = votes;
    }
    public int getUpvotesToAdd() {
    	return upvotesToAdd;
    }
    public void setUpvotesToAdd(int votes) {
    	upvotesToAdd = votes;
    }
    
    public int getDownvotes() {
    	return downvotes;
    }
    public void setDownvotes(int votes) {
    	downvotes = votes;
    }
    public int getDownvotesToAdd() {
    	return downvotesToAdd;
    }
    public void setDownvotesToAdd(int votes) {
    	downvotesToAdd = votes;
    }
    
    public int getPromotions() {
    	return promotions;
    }
    public void setPromotions(int proms) {
    	promotions = proms;
    }
    public int getPromotionsToAdd() {
    	return promotionsToAdd;
    }
    public void setPromotionsToAdd(int proms) {
    	promotionsToAdd = proms;
    }
    
    public int getJunk() {
    	return junk;
    }
    public void setJunk(int junk) {
    	this.junk = junk;
    }
    public int getJunkToAdd() {
    	return junkToAdd;
    }
    public void setJunkToAdd(int junk) {
    	junkToAdd = junk;
    }
    
    public int getNumOfComments() {
    	return numOfComments;
    }
    public void setNumOfComments(int com) {
    	numOfComments = com;
    }
    
    public ArrayList<String> getComments() {
    	return comments;
    }
    public void setComments(ArrayList<String> coms) {
    	comments = coms;
    }
    public void setComment(int pos, String com) {
    	comments.set(pos, com);
    }
    
    public ArrayList<String> getComUsernames() {
    	return commentUsernames;
    }
    public void setComUsernames(ArrayList<String> comUsers) {
    	commentUsernames = comUsers;
    }
    public void setComUsername(int pos, String comUser) {
    	commentUsernames.set(pos, comUser);
    }
    
    public ArrayList<Integer> getComUpvotes() {
    	return commentUpvotes;
    }
    public void setComUpvotes(ArrayList<Integer> comUps) {
    	commentUpvotes = comUps;
    }
    public void setComUpvotes(int pos, int comUp) {
    	commentUpvotes.set(pos, comUp);
    }
    
    public ArrayList<Integer> getComDownvotes() {
    	return commentDownvotes;
    }
    public void setComDownvotes(ArrayList<Integer> comDowns) {
    	commentDownvotes = comDowns;
    }
    public void setComDownvotes(int pos, int comDown) {
    	commentDownvotes.set(pos, comDown);
    }

    public ArrayList<Integer> getComPromotions() {
    	return commentPromotions;
    }
    public void setComPromotions(ArrayList<Integer> comProms) {
    	commentPromotions = comProms;
    }
    public void setComPromotions(int pos, int comProm) {
    	commentPromotions.set(pos, comProm);
    }

	public String getComToAdd() {
		return comToAdd;
	}

	public void setComToAdd(String comToAdd) {
		this.comToAdd = comToAdd;
	}

	public String getComUserToAdd() {
		return comUserToAdd;
	}

	public void setComUserToAdd(String comUserToAdd) {
		this.comUserToAdd = comUserToAdd;
	}

	public int getComUpToAdd() {
		return comUpToAdd;
	}

	public void setComUpToAdd(int comUpToAdd) {
		this.comUpToAdd = comUpToAdd;
	}

	public int getComDownToAdd() {
		return comDownToAdd;
	}

	public void setComDownToAdd(int comDownToAdd) {
		this.comDownToAdd = comDownToAdd;
	}

	public int getComPromToAdd() {
		return comPromToAdd;
	}

	public void setComPromToAdd(int comPromToAdd) {
		this.comPromToAdd = comPromToAdd;
	}

	public ArrayList<Integer> getComJunks() {
		return commentJunks;
	}
	public void setComJunks(ArrayList<Integer> commentJunks) {
		this.commentJunks = commentJunks;
	}
	public void setComJunks(int pos, int comJunk) {
    	commentJunks.set(pos, comJunk);
    }

	public int getComJunkToAdd() {
		return comJunkToAdd;
	}

	public void setComJunkToAdd(int comJunkToAdd) {
		this.comJunkToAdd = comJunkToAdd;
	}

	public int getComToAddTo() {
		return comToAddTo;
	}

	public void setComToAddTo(int comToAddTo) {
		this.comToAddTo = comToAddTo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    
}