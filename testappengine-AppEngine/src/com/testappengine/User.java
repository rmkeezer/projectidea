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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String username;
	private String password;
	private String email;
	private Date registerDate;
	private ArrayList<Boolean> upvotedIdeas;
	private ArrayList<Boolean> downvotedIdeas;
	private ArrayList<Boolean> junkedIdeas;
	private int upvotedIdeasToAdd;
	private int downvotedIdeasToAdd;
	private int junkedIdeasToAdd;
	private ArrayList<String> commentsUpvoted;
	private int comUpToAdd;
	private int comUpToAdd2;
	private ArrayList<String> commentsDownvoted;
	private int comDownToAdd;
	private int comDownToAdd2;
	private ArrayList<Boolean> commentsPromoted;
	private int comPromToAdd;
	private ArrayList<String> commentsJunked;
	private int comJunkToAdd;
	private int comJunkToAdd2;
	private int promotionsToAdd;
	private int promotions;

	public Key getKey() {
		return key;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public ArrayList<Boolean> getUpvotedIdeas() {
		return upvotedIdeas;
	}

	public void setUpvotedIdeas(ArrayList<Boolean> upvotedIdeas) {
		this.upvotedIdeas = upvotedIdeas;
	}

	public ArrayList<Boolean> getDownvotedIdeas() {
		return downvotedIdeas;
	}

	public void setDownvotedIdeas(ArrayList<Boolean> downvotedIdeas) {
		this.downvotedIdeas = downvotedIdeas;
	}

	/*
	 * public ArrayList<Boolean> getPromotedIdeas() { return promotedIdeas; }
	 * public void setPromotedIdeas(ArrayList<Boolean> promotedIdeas) {
	 * this.promotedIdeas = promotedIdeas; }
	 */

	public int getUpvotedIdeasToAdd() {
		return upvotedIdeasToAdd;
	}

	public void setUpvotedIdeasToAdd(int upvotedIdeasToAdd) {
		this.upvotedIdeasToAdd = upvotedIdeasToAdd;
	}

	public int getDownvotedIdeasToAdd() {
		return downvotedIdeasToAdd;
	}

	public void setDownvotedIdeasToAdd(int downvotedIdeasToAdd) {
		this.downvotedIdeasToAdd = downvotedIdeasToAdd;
	}

	/*
	 * public int getPromotedIdeasToAdd() { return promotedIdeasToAdd; } public
	 * void setPromotedIdeasToAdd(int promotedIdeasToAdd) {
	 * this.promotedIdeasToAdd = promotedIdeasToAdd; }
	 */

	public ArrayList<Boolean> getJunkedIdeas() {
		return junkedIdeas;
	}

	public void setJunkedIdeas(ArrayList<Boolean> junkedIdeas) {
		this.junkedIdeas = junkedIdeas;
	}

	public int getJunkedIdeasToAdd() {
		return junkedIdeasToAdd;
	}

	public void setJunkedIdeasToAdd(int junkedIdeasToAdd) {
		this.junkedIdeasToAdd = junkedIdeasToAdd;
	}

	public ArrayList<String> getCommentsUpvoted() {
		return commentsUpvoted;
	}

	public void setCommentsUpvoted(ArrayList<String> commentsUpvoted) {
		this.commentsUpvoted = commentsUpvoted;
	}

	public int getComUpToAdd() {
		return comUpToAdd;
	}

	public void setComUpToAdd(int comUpToAdd) {
		this.comUpToAdd = comUpToAdd;
	}

	public ArrayList<String> getCommentsDownvoted() {
		return commentsDownvoted;
	}

	public void setCommentsDownvoted(ArrayList<String> commentsDownvoted) {
		this.commentsDownvoted = commentsDownvoted;
	}

	public int getComDownToAdd() {
		return comDownToAdd;
	}

	public void setComDownToAdd(int comDownToAdd) {
		this.comDownToAdd = comDownToAdd;
	}

	public ArrayList<String> getCommentsJunked() {
		return commentsJunked;
	}

	public void setCommentsJunked(ArrayList<String> commentsJunked) {
		this.commentsJunked = commentsJunked;
	}

	public int getComJunkToAdd() {
		return comJunkToAdd;
	}

	public void setComJunkToAdd(int comJunkToAdd) {
		this.comJunkToAdd = comJunkToAdd;
	}

	public int getComPromToAdd() {
		return comPromToAdd;
	}

	public void setComPromToAdd(int comPromToAdd) {
		this.comPromToAdd = comPromToAdd;
	}

	public ArrayList<Boolean> getCommentsPromoted() {
		return commentsPromoted;
	}

	public void setCommentsPromoted(ArrayList<Boolean> commentsPromoted) {
		this.commentsPromoted = commentsPromoted;
	}

	public int getComUpToAdd2() {
		return comUpToAdd2;
	}

	public void setComUpToAdd2(int comUpToAdd2) {
		this.comUpToAdd2 = comUpToAdd2;
	}

	public int getComDownToAdd2() {
		return comDownToAdd2;
	}

	public void setComDownToAdd2(int comDownToAdd2) {
		this.comDownToAdd2 = comDownToAdd2;
	}

	public int getComJunkToAdd2() {
		return comJunkToAdd2;
	}

	public void setComJunkToAdd2(int comJunkToAdd2) {
		this.comJunkToAdd2 = comJunkToAdd2;
	}

	public int getPromotionsToAdd() {
		return promotionsToAdd;
	}

	public void setPromotionsToAdd(int promotionsToAdd) {
		this.promotionsToAdd = promotionsToAdd;
	}

	public int getPromotions() {
		return promotions;
	}

	public void setPromotions(int promotions) {
		this.promotions = promotions;
	}
}
