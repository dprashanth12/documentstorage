package com.document.service.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.document.service.model.Document;
import com.document.service.repository.DocumentRepository;

@RestController
public class DocumentStorageController {

	@Autowired
	private DocumentRepository documentRepository;

	/**
	 * Method to generate 20 characters length string for documentId
	 * 
	 * @return
	 */
	protected String getRandomString() {
		String randomChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder randomSb = new StringBuilder();
		Random rnd = new Random();
		while (randomSb.length() < 20) { // length of the random string.
			int index = (int) (rnd.nextFloat() * randomChars.length());
			randomSb.append(randomChars.charAt(index));
		}
		String randomStr = randomSb.toString();
		return randomStr;
	}

	/**
	 * Create a new record using POST requestmethod in memory db
	 * 
	 * @param document
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "/storage/documents", method = RequestMethod.POST)
	public ResponseEntity<String> createDocument(@RequestBody Document document) throws IOException {
		try {
			String documentId = getRandomString();
			document.setDocId(documentId);
			documentRepository.save(document);
			HttpHeaders headers = new HttpHeaders();
			headers.add("docId", documentId);
			headers.add("Content-Length", String.valueOf(document.getName().length()));
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

	/**
	 * Get the record from memory db for given doucment Id
	 * 
	 * @param docId
	 * @return
	 */
	@RequestMapping(path = "/storage/documents/{docId}")
	public ResponseEntity<String> getDocument(@PathVariable("docId") String docId) {
		try {
			String regex = "^[a-zA-Z0-9]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(docId);
			if (matcher.matches() && docId.length() == 20) {
				Optional<String> document = documentRepository.findAll(docId);
				HttpHeaders headers = new HttpHeaders();
				if (document.isPresent()) {
					headers.add("name", document.get());
					headers.add("Content-Length", String.valueOf(document.get().length()));
				}
				return new ResponseEntity<>(headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * update the record for given documentId using PUT requestmethod
	 * 
	 * @param docId
	 * @param name
	 * @return
	 */
	@RequestMapping(path = "/storage/documents/{docId}/{name}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateDocument(@PathVariable("docId") String docId, @PathVariable("name") String name) {
		try {
			String regex = "^[a-zA-Z0-9]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(docId);
			if (matcher.matches() && docId.length() == 20) {
				documentRepository.saveDocument(docId, name);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * Delete the record for given documentId
	 * 
	 * @param docId
	 * @return
	 */
	@RequestMapping(path = "/storage/documents/{docId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDocument(@PathVariable("docId") String docId) {
		try {
			String regex = "^[a-zA-Z0-9]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(docId);
			if (matcher.matches() && docId.length() == 20) {
				documentRepository.deleteDocument(docId);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
