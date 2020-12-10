package com.quest.etna.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.controllers.helpers.AuthHelper;
import com.quest.etna.exceptions.BadRequestException;
import com.quest.etna.exceptions.ForbiddenException;
import com.quest.etna.exceptions.InternalServerErrorException;
import com.quest.etna.exceptions.NotFoundException;
import com.quest.etna.model.dtos.auth.JwtUserDetails;
import com.quest.etna.model.dtos.boards.CreateBoardDTO;
import com.quest.etna.model.dtos.boards.UpdateBoardDTO;
import com.quest.etna.model.dtos.general.ReadSuccessDTO;
import com.quest.etna.model.entities.Board;
import com.quest.etna.model.entities.User;
import com.quest.etna.repositories.BoardRepository;
import com.quest.etna.repositories.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;


@RequestMapping("/board")
@RestController
@Tag(name = "Boards", description = "Board-related Endpoints.")
public class BoardController
{
	
	@Autowired
	private BoardRepository _boardRepository;
	
	@Autowired
	private UserRepository _userRepository;
	
	@Autowired
	private AuthHelper _authHelper;
	
	
	@GetMapping()
	public List<Board> getBoards() throws Exception
	{
		List<Board> boards = new ArrayList<>();
		JwtUserDetails currentUser = _authHelper.getCurrentUser();
		
		if (currentUser.isAdmin())
			boards = (List<Board>) _boardRepository.findAll();
		
		else
			boards = _boardRepository.findByUserId(currentUser.getId());
		
		return boards;
	}
	
	
	@GetMapping("/{id}")
	public Board getBoard(@PathVariable int id) throws Exception
	{
		Optional<Board> board = _boardRepository.findById(id);
		
		if (!board.isPresent())		
			throw new NotFoundException("Board not found.");
		
		if (!_authHelper.currentUserCanAccessBoard(board.get()))
			throw new ForbiddenException("You are not authorized to access this Board.");

		return board.get();
	}
	
	
	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Board> createBoard(@RequestBody CreateBoardDTO boardToCreate)
	{
		Board boardToPersist;
    	
    	if (boardToCreate.getTitle() == null)
    		throw new BadRequestException("Title is required.");
    	
    	try
    	{
    		boardToPersist = new Board(
				boardToCreate.getTitle()
			);
    		
    		_boardRepository.save(boardToPersist);
    	}

    	catch (Exception e)
    	{
    		throw new InternalServerErrorException(e.getMessage());
    	}

    	
    	return new ResponseEntity<Board>(boardToPersist, HttpStatus.CREATED);
	}
	
	
	@PatchMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Board> updateBoard(
		@PathVariable int id,
		@RequestBody UpdateBoardDTO fieldsToUpdate
	) throws Exception
	{
		if (fieldsToUpdate.getTitle() == "")
    		throw new BadRequestException("Title cannot be empty.");
		
		Optional<Board> boardToUpdate = _boardRepository.findById(id);
		
		if (!boardToUpdate.isPresent())
			throw new NotFoundException("Board not found.");
		
		Board boardToPersist = boardToUpdate.get();
		
		if (fieldsToUpdate.getTitle() != null)
			boardToPersist.setTitle(fieldsToUpdate.getTitle());
		
		if (fieldsToUpdate.getUserIds() != null)
		{
			Set<User> boardUsers = _userRepository.findByIdIn(fieldsToUpdate.getUserIds());
			
			if (boardUsers != null)
				boardToPersist.setUsers(boardUsers);
		}
		
		Board savedBoard = _boardRepository.save(boardToPersist);		

		return new ResponseEntity<Board>(savedBoard, HttpStatus.OK);
	}

	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ReadSuccessDTO> deleteBoard(@PathVariable int id) throws Exception
	{
		ReadSuccessDTO response = new ReadSuccessDTO(false);
		
		Optional<Board> boardToDelete = _boardRepository.findById(id);
		
		if (!boardToDelete.isPresent())
			throw new NotFoundException("Board not found.");
		
		try
		{
			boardToDelete.get().getUsers().clear();
			_boardRepository.delete(boardToDelete.get());
			response.setSuccess(true);
		}
		
		catch (Exception e)
		{
			response.setSuccess(false);
		}
		
		return new ResponseEntity<ReadSuccessDTO>(response, HttpStatus.OK);
	}
}
