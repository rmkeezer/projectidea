/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-08-01 at 04:48:31 UTC 
 * Modify at your own risk.
 */

package com.testappengine.ideaendpoint.model;

/**
 * Model definition for Idea.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the ideaendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Idea extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String category;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer comDownToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.Integer> comDownvotes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer comJunkToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.Integer> comJunks;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer comPromToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.Integer> comPromotions;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String comToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer comToAddTo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer comUpToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.Integer> comUpvotes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String comUserToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> comUsernames;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> comments;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer downvotes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer downvotesToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String idea;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer junk;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer junkToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer numOfComments;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime postDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String poster;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer promotions;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer promotionsToAdd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer upvotes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer upvotesToAdd;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCategory() {
    return category;
  }

  /**
   * @param category category or {@code null} for none
   */
  public Idea setCategory(java.lang.String category) {
    this.category = category;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getComDownToAdd() {
    return comDownToAdd;
  }

  /**
   * @param comDownToAdd comDownToAdd or {@code null} for none
   */
  public Idea setComDownToAdd(java.lang.Integer comDownToAdd) {
    this.comDownToAdd = comDownToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Integer> getComDownvotes() {
    return comDownvotes;
  }

  /**
   * @param comDownvotes comDownvotes or {@code null} for none
   */
  public Idea setComDownvotes(java.util.List<java.lang.Integer> comDownvotes) {
    this.comDownvotes = comDownvotes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getComJunkToAdd() {
    return comJunkToAdd;
  }

  /**
   * @param comJunkToAdd comJunkToAdd or {@code null} for none
   */
  public Idea setComJunkToAdd(java.lang.Integer comJunkToAdd) {
    this.comJunkToAdd = comJunkToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Integer> getComJunks() {
    return comJunks;
  }

  /**
   * @param comJunks comJunks or {@code null} for none
   */
  public Idea setComJunks(java.util.List<java.lang.Integer> comJunks) {
    this.comJunks = comJunks;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getComPromToAdd() {
    return comPromToAdd;
  }

  /**
   * @param comPromToAdd comPromToAdd or {@code null} for none
   */
  public Idea setComPromToAdd(java.lang.Integer comPromToAdd) {
    this.comPromToAdd = comPromToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Integer> getComPromotions() {
    return comPromotions;
  }

  /**
   * @param comPromotions comPromotions or {@code null} for none
   */
  public Idea setComPromotions(java.util.List<java.lang.Integer> comPromotions) {
    this.comPromotions = comPromotions;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getComToAdd() {
    return comToAdd;
  }

  /**
   * @param comToAdd comToAdd or {@code null} for none
   */
  public Idea setComToAdd(java.lang.String comToAdd) {
    this.comToAdd = comToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getComToAddTo() {
    return comToAddTo;
  }

  /**
   * @param comToAddTo comToAddTo or {@code null} for none
   */
  public Idea setComToAddTo(java.lang.Integer comToAddTo) {
    this.comToAddTo = comToAddTo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getComUpToAdd() {
    return comUpToAdd;
  }

  /**
   * @param comUpToAdd comUpToAdd or {@code null} for none
   */
  public Idea setComUpToAdd(java.lang.Integer comUpToAdd) {
    this.comUpToAdd = comUpToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Integer> getComUpvotes() {
    return comUpvotes;
  }

  /**
   * @param comUpvotes comUpvotes or {@code null} for none
   */
  public Idea setComUpvotes(java.util.List<java.lang.Integer> comUpvotes) {
    this.comUpvotes = comUpvotes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getComUserToAdd() {
    return comUserToAdd;
  }

  /**
   * @param comUserToAdd comUserToAdd or {@code null} for none
   */
  public Idea setComUserToAdd(java.lang.String comUserToAdd) {
    this.comUserToAdd = comUserToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getComUsernames() {
    return comUsernames;
  }

  /**
   * @param comUsernames comUsernames or {@code null} for none
   */
  public Idea setComUsernames(java.util.List<java.lang.String> comUsernames) {
    this.comUsernames = comUsernames;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getComments() {
    return comments;
  }

  /**
   * @param comments comments or {@code null} for none
   */
  public Idea setComments(java.util.List<java.lang.String> comments) {
    this.comments = comments;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getDownvotes() {
    return downvotes;
  }

  /**
   * @param downvotes downvotes or {@code null} for none
   */
  public Idea setDownvotes(java.lang.Integer downvotes) {
    this.downvotes = downvotes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getDownvotesToAdd() {
    return downvotesToAdd;
  }

  /**
   * @param downvotesToAdd downvotesToAdd or {@code null} for none
   */
  public Idea setDownvotesToAdd(java.lang.Integer downvotesToAdd) {
    this.downvotesToAdd = downvotesToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIdea() {
    return idea;
  }

  /**
   * @param idea idea or {@code null} for none
   */
  public Idea setIdea(java.lang.String idea) {
    this.idea = idea;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getJunk() {
    return junk;
  }

  /**
   * @param junk junk or {@code null} for none
   */
  public Idea setJunk(java.lang.Integer junk) {
    this.junk = junk;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getJunkToAdd() {
    return junkToAdd;
  }

  /**
   * @param junkToAdd junkToAdd or {@code null} for none
   */
  public Idea setJunkToAdd(java.lang.Integer junkToAdd) {
    this.junkToAdd = junkToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Idea setKey(java.lang.Long key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getNumOfComments() {
    return numOfComments;
  }

  /**
   * @param numOfComments numOfComments or {@code null} for none
   */
  public Idea setNumOfComments(java.lang.Integer numOfComments) {
    this.numOfComments = numOfComments;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getPostDate() {
    return postDate;
  }

  /**
   * @param postDate postDate or {@code null} for none
   */
  public Idea setPostDate(com.google.api.client.util.DateTime postDate) {
    this.postDate = postDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPoster() {
    return poster;
  }

  /**
   * @param poster poster or {@code null} for none
   */
  public Idea setPoster(java.lang.String poster) {
    this.poster = poster;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPromotions() {
    return promotions;
  }

  /**
   * @param promotions promotions or {@code null} for none
   */
  public Idea setPromotions(java.lang.Integer promotions) {
    this.promotions = promotions;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPromotionsToAdd() {
    return promotionsToAdd;
  }

  /**
   * @param promotionsToAdd promotionsToAdd or {@code null} for none
   */
  public Idea setPromotionsToAdd(java.lang.Integer promotionsToAdd) {
    this.promotionsToAdd = promotionsToAdd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getUpvotes() {
    return upvotes;
  }

  /**
   * @param upvotes upvotes or {@code null} for none
   */
  public Idea setUpvotes(java.lang.Integer upvotes) {
    this.upvotes = upvotes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getUpvotesToAdd() {
    return upvotesToAdd;
  }

  /**
   * @param upvotesToAdd upvotesToAdd or {@code null} for none
   */
  public Idea setUpvotesToAdd(java.lang.Integer upvotesToAdd) {
    this.upvotesToAdd = upvotesToAdd;
    return this;
  }

  @Override
  public Idea set(String fieldName, Object value) {
    return (Idea) super.set(fieldName, value);
  }

  @Override
  public Idea clone() {
    return (Idea) super.clone();
  }

}
