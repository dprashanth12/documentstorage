package com.document.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.document.service.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long>{

	 @Query("SELECT doc.name FROM Document doc where doc.docId = :docId") 
	Optional<String> findAll(String docId);

	 @Transactional
	 @Modifying
	 @Query("update Document as doc set  doc.name = :name  where doc.docId = :docId") 
	 void saveDocument(String docId,String name);

	 @Transactional
	 @Modifying
	 @Query("delete from Document doc where doc.docId = :docId")
	 void deleteDocument(String docId);

}
