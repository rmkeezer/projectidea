package com.testappengine;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * An entity for Android device information.
 * 
 * Its associated endpoint, DeviceInfoEndpoint.java, was directly generated from
 * this class - the Google Plugin for Eclipse allows you to generate endpoints
 * directly from entities!
 * 
 * DeviceInfoEndpoint.java will be used for registering devices with this App
 * Engine application. Registered devices will receive messages broadcast by
 * this application over Google Cloud Messaging (GCM). If you'd like to take a
 * look at the broadcasting code, check out MessageEndpoint.java.
 * 
 * For more information, see
 * http://developers.google.com/eclipse/docs/cloud_endpoints.
 * 
 * NOTE: This DeviceInfoEndpoint.java does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Entity
// DeviceInfoEndpoint has NO AUTHENTICATION - it is an OPEN ENDPOINT!
public class DeviceInfo {

	/*
	 * The Google Cloud Messaging registration token for the device. This token
	 * indicates that the device is able to receive messages sent via GCM.
	 */
	@Id
	private String deviceRegistrationID;

	/*
	 * Some identifying information about the device, such as its manufacturer
	 * and product name.
	 */
	private String deviceInformation;

	/*
	 * Timestamp indicating when this device registered with the application.
	 */
	private long timestamp;

	private ArrayList<Boolean> upvotedIdeas;
	private ArrayList<Boolean> downvotedIdeas;
	private ArrayList<Boolean> promotedIdeas;
	private ArrayList<Boolean> junkedIdeas;
	private int upvotedIdeasToAdd;
	private int downvotedIdeasToAdd;
	private int promotedIdeasToAdd;
	private int junkedIdeasToAdd;
    private ArrayList<String> commentsUpvoted;
    private int comUpToAdd;
    private int comUpToAdd2;
    private ArrayList<String> commentsDownvoted;
    private int comDownToAdd;
    private int comDownToAdd2;
    private ArrayList<String> commentsPromoted;
    private int comPromToAdd;
    private ArrayList<String> commentsJunked;
    private int comJunkToAdd;
    private int comJunkToAdd2;

	public String getDeviceRegistrationID() {
		return deviceRegistrationID;
	}

	public String getDeviceInformation() {
		return this.deviceInformation;
	}

	public void setDeviceRegistrationID(String deviceRegistrationID) {
		this.deviceRegistrationID = deviceRegistrationID;
	}

	public void setDeviceInformation(String deviceInformation) {
		this.deviceInformation = deviceInformation;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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

	public ArrayList<Boolean> getPromotedIdeas() {
		return promotedIdeas;
	}

	public void setPromotedIdeas(ArrayList<Boolean> promotedIdeas) {
		this.promotedIdeas = promotedIdeas;
	}

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

	public int getPromotedIdeasToAdd() {
		return promotedIdeasToAdd;
	}

	public void setPromotedIdeasToAdd(int promotedIdeasToAdd) {
		this.promotedIdeasToAdd = promotedIdeasToAdd;
	}

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

	public ArrayList<String> getCommentsPromoted() {
		return commentsPromoted;
	}

	public void setCommentsPromoted(ArrayList<String> commentsPromoted) {
		this.commentsPromoted = commentsPromoted;
	}

	public int getComPromToAdd() {
		return comPromToAdd;
	}

	public void setComPromToAdd(int comPromToAdd) {
		this.comPromToAdd = comPromToAdd;
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
}
