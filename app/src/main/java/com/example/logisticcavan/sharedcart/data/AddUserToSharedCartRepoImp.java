package com.example.logisticcavan.sharedcart.data;

import com.example.logisticcavan.sharedcart.domain.repo.AddUserToSharedCartRepo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public class AddUserToSharedCartRepoImp implements AddUserToSharedCartRepo {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    public AddUserToSharedCartRepoImp(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }


    /**
     * @noinspection unchecked
     */
    @Override
    public Single<String> CheckUserExistenceAndAddUserToSharedCart(String userEmail) {
        return Single.create(
                emitter -> isUserEmailRegistered(userEmail)
                        .addOnSuccessListener(isRegistered -> {
                            if (isRegistered) {
                                // If the email exists, proceed with the SharedCart operation
                                updateSharedCartWithUserEmail(userEmail,emitter);
                            } else {
                                // Handle the case when the email is not registered
                                System.out.println("Email not found in the users collection.");
                                emitter.onSuccess("Email not found");
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure when checking the email
                            System.err.println("Error checking if email exists: " + e.getMessage());
                        })
        );
    }
    private Task<Boolean> isUserEmailRegistered(String email) {
        DocumentReference userDocRef = firebaseFirestore.collection("users").document(email);
        return userDocRef.get().continueWith(task -> task.isSuccessful() && task.getResult().exists());
    }
    /** @noinspection unchecked*/
    private void updateSharedCartWithUserEmail(String userEmail,SingleEmitter<String> emitter) {
        firebaseFirestore.collection("SharedCart")
                .whereArrayContains("userIds", getUserEmail())  // Check if the current user's email is already in the userIds array
                .get()
                .addOnSuccessListener(v -> {
                    if (!v.isEmpty()) {
                        // Get the first document that matches the query
                        DocumentSnapshot document = v.getDocuments().get(0);

                        // Retrieve the 'userIds' list
                        List<String> oldUserIds = (List<String>) document.get("userIds");

                        if (oldUserIds != null) {
                            // Check if the email is already in the list
                            if (!oldUserIds.contains(userEmail)) {
                                // Add the email to the userIds list
                                oldUserIds.add(userEmail);
                                emitter.onSuccess("User email added successfully");
                                updateDocumentUserIds(document, oldUserIds);  // Update the document with the new userIds list
                            } else {
                                System.out.println("User email already exists");
                                emitter.onSuccess("User email already exists");
                            }
                        } else {
                            System.err.println("The 'userIds' field is null or not a list.");
                        }
                    } else {
                        System.err.println("No document found that contains the current user's email.");
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error fetching documents from SharedCart: " + e.getMessage());
                });
    }
    private void updateDocumentUserIds(DocumentSnapshot document, List<String> updatedUserIds) {
        document.getReference().update("userIds", updatedUserIds)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Updated User IDs: " + Arrays.toString(updatedUserIds.toArray()));
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error updating user IDs: " + e.getMessage());
                });
    }

    private String getUserEmail() {
        return Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
    }
}
