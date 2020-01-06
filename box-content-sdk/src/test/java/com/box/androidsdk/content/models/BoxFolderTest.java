package com.box.androidsdk.content.models;

import org.junit.Assert;
import org.junit.Test;

public class BoxFolderTest {

    @Test
    public void testParseJson() {
        String folderJson = "{\"type\":\"folder\",\"id\":\"11446498\",\"sequence_id\":\"1\",\"etag\":\"1\",\"name\":\"Pictures\",\"created_at\":\"2012-12-12T10:53:43-08:00\",\"modified_at\":\"2012-12-12T11:15:04-08:00\",\"description\":\"Some pictures I took\",\"size\":629644,\"path_collection\":{\"entries\":[{\"type\":\"folder\",\"id\":\"0\",\"name\":\"All Files\"}],\"total_count\":1},\"created_by\":{\"type\":\"user\",\"id\":\"17738362\",\"name\":\"sean rose\",\"login\":\"sean@box.com\"},\"modified_by\":{\"type\":\"user\",\"id\":\"17738362\",\"name\":\"sean rose\",\"login\":\"sean@box.com\"},\"owned_by\":{\"type\":\"user\",\"id\":\"17738362\",\"name\":\"sean rose\",\"login\":\"sean@box.com\"},\"shared_link\":{\"url\":\"https://www.box.com/s/vspke7y05sb214wjokpk\",\"is_password_enabled\":false,\"download_count\":0,\"preview_count\":0,\"access\":\"open\",\"permissions\":{\"can_download\":true,\"can_preview\":true}},\"folder_upload_email\":{\"access\":\"open\",\"email\":\"upload.Picture.k13sdz1@u.box.com\"},\"parent\":{\"type\":\"folder\",\"id\":\"0\",\"name\":\"All Files\"},\"item_status\":\"active\",\"item_collection\":{\"entries\":[{\"type\":\"file\",\"id\":\"5000948880\",\"sequence_id\":\"3\",\"etag\":\"3\",\"sha1\":\"134b65991ed521fcfe4724b7d814ab8ded5185dc\",\"name\":\"tigers.jpeg\"}],\"total_count\":1,\"offset\":0,\"limit\":100}}";

        BoxFolder folder = new BoxFolder();
        folder.createFromJson(folderJson);
        String json = folder.toJson();
        Assert.assertEquals(folderJson, json);
    }

    @Test
    public void testParseToStringConsistency() {
        String originalJson = "{\"type\":\"folder\",\"id\":\"1926745173\",\"etag\":\"0\",\"sequence_id\":\"0\",\"name\":\".ATest\",\"created_at\":\"2014-05-07T10:41:19-08:00\",\"modified_at\":\"2014-12-03T17:13:16-08:00\",\"description\":\"\",\"path_collection\":{\"total_count\":1,\"entries\":[{\"type\":\"folder\",\"id\":\"0\",\"sequence_id\":null,\"etag\":null,\"name\":\"All Files\"}]},\"created_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"modified_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"trashed_at\":null,\"purged_at\":null,\"owned_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"shared_link\":{\"url\":\"https://app.box.com/s/6spus7b06fi12bncxim0\",\"download_url\":null,\"vanity_url\":null,\"effective_access\":\"collaborators\",\"is_password_enabled\":false,\"unshared_at\":null,\"download_count\":0,\"preview_count\":0,\"access\":\"collaborators\",\"permissions\":{\"can_download\":true,\"can_preview\":true}},\"parent\":{\"type\":\"folder\",\"id\":\"0\",\"sequence_id\":null,\"etag\":null,\"name\":\"All Files\"},\"item_status\":\"active\",\"permissions\":{\"can_download\":true,\"can_upload\":true,\"can_rename\":true,\"can_delete\":true,\"can_share\":true,\"can_invite_collaborator\":true,\"can_set_share_access\":true},\"allowed_shared_link_access_levels\":[\"collaborators\",\"open\"],\"tags\":[\"out frying pan into fire\",\"test\"],\"collections\":[{\"type\":\"collection\",\"id\":\"10041\",\"name\":\"Favorites\",\"collection_type\":\"favorites\"}],\"folder_upload_email\":null,\"has_collaborations\":false,\"sync_state\":\"not_synced\",\"can_non_owners_invite\":true,\"is_externally_owned\":false,\"allowed_invitee_roles\":[\"editor\",\"viewer\"],\"size\":194000400,\"content_created_at\":\"2014-05-07T10:41:19-08:00\",\"content_modified_at\":\"2014-12-03T17:13:16-08:00\",\"item_collection\":{\"total_count\":5,\"entries\":[{\"type\":\"folder\",\"id\":\"2087113040\",\"etag\":\"0\",\"sequence_id\":\"0\",\"name\":\"0sharedviewer\",\"created_at\":\"2014-06-13T14:53:50-08:00\",\"modified_at\":\"2014-06-13T14:53:50-08:00\",\"description\":\"\",\"path_collection\":{\"total_count\":2,\"entries\":[{\"type\":\"folder\",\"id\":\"0\",\"sequence_id\":null,\"etag\":null,\"name\":\"All Files\"},{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"}]},\"created_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"modified_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"trashed_at\":null,\"purged_at\":null,\"owned_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"shared_link\":null,\"parent\":{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"},\"item_status\":\"active\",\"permissions\":{\"can_download\":true,\"can_upload\":true,\"can_rename\":true,\"can_delete\":true,\"can_share\":true,\"can_invite_collaborator\":true,\"can_set_share_access\":true},\"allowed_shared_link_access_levels\":[\"collaborators\",\"open\"],\"tags\":[],\"collections\":[],\"folder_upload_email\":null,\"has_collaborations\":false,\"sync_state\":\"not_synced\",\"can_non_owners_invite\":true,\"is_externally_owned\":false,\"allowed_invitee_roles\":[\"editor\",\"viewer\"],\"size\":259743,\"content_created_at\":\"2014-05-07T12:14:51-08:00\",\"content_modified_at\":\"2014-06-13T13:21:01-08:00\"},{\"type\":\"folder\",\"id\":\"2151100388\",\"etag\":\"0\",\"sequence_id\":\"0\",\"name\":\"Tttt\",\"created_at\":\"2014-06-30T18:51:09-08:00\",\"modified_at\":\"2014-11-13T13:17:38-08:00\",\"description\":\"\",\"path_collection\":{\"total_count\":2,\"entries\":[{\"type\":\"folder\",\"id\":\"0\",\"sequence_id\":null,\"etag\":null,\"name\":\"All Files\"},{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"}]},\"created_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"modified_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"trashed_at\":null,\"purged_at\":null,\"owned_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"shared_link\":null,\"parent\":{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"},\"item_status\":\"active\",\"permissions\":{\"can_download\":true,\"can_upload\":true,\"can_rename\":true,\"can_delete\":true,\"can_share\":true,\"can_invite_collaborator\":true,\"can_set_share_access\":true},\"allowed_shared_link_access_levels\":[\"collaborators\",\"open\"],\"tags\":[],\"collections\":[],\"folder_upload_email\":null,\"has_collaborations\":false,\"sync_state\":\"not_synced\",\"can_non_owners_invite\":true,\"is_externally_owned\":false,\"allowed_invitee_roles\":[\"editor\",\"viewer\"],\"size\":10404925,\"content_created_at\":\"2014-06-30T18:51:09-08:00\",\"content_modified_at\":\"2014-11-13T13:17:38-08:00\"},{\"type\":\"file\",\"id\":\"16934859467\",\"etag\":\"1\",\"sequence_id\":\"1\",\"name\":\"1323143520881.jpg\",\"created_at\":\"2014-05-08T13:39:17-08:00\",\"modified_at\":\"2014-05-08T13:39:28-08:00\",\"description\":\"\",\"path_collection\":{\"total_count\":2,\"entries\":[{\"type\":\"folder\",\"id\":\"0\",\"sequence_id\":null,\"etag\":null,\"name\":\"All Files\"},{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"}]},\"created_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"modified_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"trashed_at\":null,\"purged_at\":null,\"owned_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"shared_link\":{\"url\":\"https://app.box.com/s/te8zyope5acx8pijbgpw\",\"download_url\":\"https://app.box.com/shared/static/te8zyope5acx8pijbgpw.jpg\",\"vanity_url\":null,\"effective_access\":\"open\",\"is_password_enabled\":false,\"unshared_at\":null,\"download_count\":0,\"preview_count\":0,\"access\":\"open\",\"permissions\":{\"can_download\":true,\"can_preview\":true}},\"parent\":{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"},\"item_status\":\"active\",\"permissions\":{\"can_download\":true,\"can_preview\":true,\"can_upload\":true,\"can_comment\":true,\"can_rename\":true,\"can_delete\":true,\"can_share\":true,\"can_set_share_access\":true},\"allowed_shared_link_access_levels\":[\"collaborators\",\"open\"],\"tags\":[],\"collections\":[],\"size\":11679,\"content_created_at\":\"2014-05-08T13:39:17-08:00\",\"content_modified_at\":\"2014-05-08T13:39:17-08:00\"},{\"type\":\"file\",\"id\":\"17282088875\",\"etag\":\"0\",\"sequence_id\":\"0\",\"name\":\"CAM00010.mp4\",\"created_at\":\"2014-05-19T16:23:03-08:00\",\"modified_at\":\"2014-05-19T16:23:03-08:00\",\"description\":\"\",\"path_collection\":{\"total_count\":2,\"entries\":[{\"type\":\"folder\",\"id\":\"0\",\"sequence_id\":null,\"etag\":null,\"name\":\"All Files\"},{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"}]},\"created_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"modified_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"trashed_at\":null,\"purged_at\":null,\"owned_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"shared_link\":null,\"parent\":{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"},\"item_status\":\"active\",\"permissions\":{\"can_download\":true,\"can_preview\":true,\"can_upload\":true,\"can_comment\":true,\"can_rename\":true,\"can_delete\":true,\"can_share\":true,\"can_set_share_access\":true},\"allowed_shared_link_access_levels\":[\"collaborators\",\"open\"],\"tags\":[],\"collections\":[],\"size\":183323625,\"content_created_at\":\"2014-05-19T16:23:03-08:00\",\"content_modified_at\":\"2014-05-19T16:23:03-08:00\"},{\"type\":\"file\",\"id\":\"18591223140\",\"etag\":\"0\",\"sequence_id\":\"0\",\"name\":\"keepass.kdb\",\"created_at\":\"2014-06-30T16:30:59-08:00\",\"modified_at\":\"2014-06-30T16:30:59-08:00\",\"description\":\"\",\"path_collection\":{\"total_count\":2,\"entries\":[{\"type\":\"folder\",\"id\":\"0\",\"sequence_id\":null,\"etag\":null,\"name\":\"All Files\"},{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"}]},\"created_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"modified_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"trashed_at\":null,\"purged_at\":null,\"owned_by\":{\"type\":\"user\",\"id\":\"12517880\",\"name\":\"Mobile Boxer\",\"login\":\"mobileboxer@gmail.com\"},\"shared_link\":null,\"parent\":{\"type\":\"folder\",\"id\":\"1926745173\",\"sequence_id\":\"0\",\"etag\":\"0\",\"name\":\".ATest\"},\"item_status\":\"active\",\"permissions\":{\"can_download\":true,\"can_preview\":true,\"can_upload\":true,\"can_comment\":true,\"can_rename\":true,\"can_delete\":true,\"can_share\":true,\"can_set_share_access\":true},\"allowed_shared_link_access_levels\":[\"collaborators\",\"open\"],\"tags\":[],\"collections\":[],\"size\":428,\"content_created_at\":\"2014-06-30T16:30:59-08:00\",\"content_modified_at\":\"2014-06-30T16:30:59-08:00\"}],\"offset\":0,\"limit\":100,\"order\":[{\"by\":\"type\",\"direction\":\"ASC\"},{\"by\":\"name\",\"direction\":\"ASC\"}]}}";
        BoxFolder folder = new BoxFolder();
        folder.createFromJson(originalJson);
        Assert.assertEquals(1, folder.getCollections().size());

        // Check collections and tags
        Assert.assertEquals("Favorites", folder.getCollections().get(0).getName());
        Assert.assertEquals(2, folder.getTags().size());
        Assert.assertEquals("test", folder.getTags().get(1));

        // Output object json and read it back in
        String generatedJson = folder.toJson();
        BoxFolder newFolder = new BoxFolder();
        newFolder.createFromJson(generatedJson);
        String actual = newFolder.toJson();

        // Check consistency with original JSON
        Assert.assertEquals(originalJson, actual);
    }
}

