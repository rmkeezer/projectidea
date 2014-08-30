package com.testappengine;

import com.testappengine.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@Api(name = "deviceinfoendpoint", namespace = @ApiNamespace(ownerDomain = "testappengine.com", ownerName = "testappengine.com", packagePath = ""))
public class DeviceInfoEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listDeviceInfo")
	public CollectionResponse<DeviceInfo> listDeviceInfo(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<DeviceInfo> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from DeviceInfo as DeviceInfo");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<DeviceInfo>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (DeviceInfo obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<DeviceInfo> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getDeviceInfo")
	public DeviceInfo getDeviceInfo(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		DeviceInfo deviceinfo = null;
		try {
			deviceinfo = mgr.find(DeviceInfo.class, id);
		} finally {
			mgr.close();
		}
		return deviceinfo;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param deviceinfo the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertDeviceInfo")
	public DeviceInfo insertDeviceInfo(DeviceInfo deviceinfo) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsDeviceInfo(deviceinfo)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(deviceinfo);
		} finally {
			mgr.close();
		}
		return deviceinfo;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param deviceinfo the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateDeviceInfo")
	public DeviceInfo updateDeviceInfo(DeviceInfo deviceinfo) {
		EntityManager mgr = getEntityManager();
		EntityTransaction transaction = mgr.getTransaction();
		DeviceInfo updatedDev = new DeviceInfo();
		try {
			transaction.begin();
			updatedDev = mgr.find(DeviceInfo.class, deviceinfo.getDeviceRegistrationID());
			int key = deviceinfo.getUpvotedIdeasToAdd();
			if(key >= 0) {
				
				List<Boolean> tempArr = updatedDev.getUpvotedIdeas();
				if(tempArr.size() <= key)
					for(int i=tempArr.size(); i<=key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}
			key = deviceinfo.getDownvotedIdeasToAdd();
			if(key >= 0) {
				List<Boolean> tempArr = updatedDev.getDownvotedIdeas();
				if(tempArr.size() <= key)
					for(int i=tempArr.size(); i<=key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}
			key = deviceinfo.getPromotedIdeasToAdd();
			if(key >= 0) {
				List<Boolean> tempArr = updatedDev.getPromotedIdeas();
				if(tempArr.size() <= key)
					for(int i=tempArr.size(); i<=key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}
			key = deviceinfo.getJunkedIdeasToAdd();
			if(key >= 0) {
				List<Boolean> tempArr = updatedDev.getJunkedIdeas();
				if(tempArr.size() <= key)
					for(int i=tempArr.size(); i<=key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}
			// Uses a string to store upvote data per comment section.
			// Google Datastore does not support Maps or Matrices.
			key = deviceinfo.getComUpToAdd();
			int key2 = deviceinfo.getComUpToAdd2();
			if (key != -1 && key2 != -1) {
				List<String> tempArr = updatedDev.getCommentsUpvoted();
				if (tempArr.size() <= key) {
					for (int i=tempArr.size(); i<=key; i++) {
						tempArr.add("0");
					}
				}
				String filler = "";
				String comStr = tempArr.get(key);
				if (comStr.length() <= key2*2) {
					for (int i=comStr.length(); i<=key2*2; i+=2) {
						filler += ",0";
					}
					if (filler.length() == 0 && comStr.length() == 0) {
						filler += "1";
					} else if (filler.length() == 0) {
						filler += ",1";
					} else {
						filler = filler.substring(0, filler.length()-1) + "1";
					}
					tempArr.set(key, comStr + filler);
				} else {
					if(key2 == 0) {
						tempArr.set(key, "1" + comStr.substring(1));
					} else {
						tempArr.set(key, comStr.substring(0, (key2*2)) + "1" + comStr.substring((key2*2)+1));
					}
				}
			}
			// Uses a string to store downvote data per comment section.
			// Google Datastore does not support Maps or Matrices.
			key = deviceinfo.getComDownToAdd();
		    key2 = deviceinfo.getComDownToAdd2();
			if (key != -1 && key2 != -1) {
				List<String> tempArr = updatedDev.getCommentsDownvoted();
				if (tempArr.size() <= key) {
					for (int i=tempArr.size(); i<=key; i++) {
						tempArr.add("0");
					}
				}
				String filler = "";
				String comStr = tempArr.get(key);
				if (comStr.length() <= key2*2) {
					for (int i=comStr.length(); i<=key2*2; i+=2) {
						filler += ",0";
					}
					if (filler.length() == 0 && comStr.length() == 0) {
						filler += "1";
					} else if (filler.length() == 0) {
						filler += ",1";
					} else {
						filler = filler.substring(0, filler.length()-1) + "1";
					}
					tempArr.set(key, comStr + filler);
				} else {
					if(key2 == 0) {
						tempArr.set(key, "1" + comStr.substring(1));
					} else {
						tempArr.set(key, comStr.substring(0, (key2*2)) + "1" + comStr.substring((key2*2)+1));
					}
				}
			}
			// Uses a string to store downvote data per comment section.
			// Google Datastore does not support Maps or Matrices.
			key = deviceinfo.getComJunkToAdd();
		    key2 = deviceinfo.getComJunkToAdd2();
			if (key != -1 && key2 != -1) {
				List<String> tempArr = updatedDev.getCommentsJunked();
				if (tempArr.size() <= key) {
					for (int i=tempArr.size(); i<=key; i++) {
						tempArr.add("0");
					}
				}
				String filler = "";
				String comStr = tempArr.get(key);
				if (comStr.length() <= key2*2) {
					for (int i=comStr.length(); i<=key2*2; i+=2) {
						filler += ",0";
					}
					if (filler.length() == 0 && comStr.length() == 0) {
						filler += "1";
					} else if (filler.length() == 0) {
						filler += ",1";
					} else {
						filler = filler.substring(0, filler.length()-1) + "1";
					}
					tempArr.set(key, comStr + filler);
				} else {
					if(key2 == 0) {
						tempArr.set(key, "1" + comStr.substring(1));
					} else {
						tempArr.set(key, comStr.substring(0, (key2*2)) + "1" + comStr.substring((key2*2)+1));
					}
				}
			}
			transaction.commit();
			mgr.refresh(updatedDev);
		} finally {
			mgr.close();
		}
		return deviceinfo;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeDeviceInfo")
	public void removeDeviceInfo(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			DeviceInfo deviceinfo = mgr.find(DeviceInfo.class, id);
			mgr.remove(deviceinfo);
		} finally {
			mgr.close();
		}
	}

	private boolean containsDeviceInfo(DeviceInfo deviceinfo) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			DeviceInfo item = mgr.find(DeviceInfo.class,
					deviceinfo.getDeviceRegistrationID());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
