package edu.sjsu.cmpe275.lab1;

import java.util.UUID;
public interface SecretService {
    /**
     * Store a secrete in the service. A new “secret” record is already created, identified by randomly generated UUID, with the current user as the owner of the secret.
     * @param userId the ID of the current user
     * @param secret the secret to be stored. No duplication or null check is performed. * @return always return a new UUID for the given secret
     */
    UUID storeSecret(String userId, Secret secret);

    /**
     * Read a secret by ID
     *
     * @param userId   the ID of the current user
     * @param secretId the ID of the secret being requested * @return the requested secret
     */
    Secret readSecret(String userId, UUID secretId);

    /**
     * Share a secret with another user. The secret may not have been created by the current user. * @param userId the ID of the current user
     *
     * @param secretId     the ID of the secret being shared
     * @param targetUserId the ID of the user to share the secret with
     */
    void shareSecret(String userId, UUID secretId, String targetUserId);

    /**
     * Unshare the current user's own secret with another user.
     *
     * @param userId
     * @param targetUserId
     */
    void unshareSecret(UUID userId, String targetUserId);
}