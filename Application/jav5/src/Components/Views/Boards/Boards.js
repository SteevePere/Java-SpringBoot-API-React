import React, { useState, useEffect } from 'react';

import BoardListView from './List/BoardListView';

import { notifications } from './Notifications/notifications';

import { request } from 'Lib/request.js';
import { auth } from 'Lib/auth.js';


const Boards = (props) =>
{

	const [boards, setBoards] = useState([]);
	const [allBoards, setAllBoards] = useState([]);
	const [users, setUsers] = useState([]);
	const [boardToManage, setBoardToManage] = useState(undefined);
	const [manageUsersModalIsOpen, setManageUsersModalIsOpen] = useState(false);
	const [boardToEdit, setBoardToEdit] = useState(undefined);
	const [boardsLoading, setBoardsLoading] = useState(false);

	const isAdmin = auth.isAdmin();


	useEffect(() =>
		{
			getBoards();
			getUsers();
		},
		[]
	);


	const getBoards = () =>
	{
		setBoardsLoading(true);

		request.get(
			'board',
			{},
			(boards) =>
			{
				setBoards(boards);
				setAllBoards(boards);
			},
			(e) => console.error(e),
			() => setBoardsLoading(false),
		);
	}


	const getUsers = () =>
	{
		request.get(
			'user',
			{},
			(users) => setUsers(users),
			(e) => console.error(e),
		);
	}


	const openManageUsersModal = (board) =>
	{
		setManageUsersModalIsOpen(true);

		if (board && board.users)
		{
			setBoardToManage({
				...board,
				userIds: board.users.map(user => user.id),
			});
		}
	}


	const closeManageUsersModal = () =>
	{
		setManageUsersModalIsOpen(false);
		setBoardToManage(undefined);
	}


	const handleBoardUsersSelectChange = (userIds) =>
	{
		setBoardToManage({
			...boardToManage,
			userIds: userIds,
		});
	}


	const saveBoardUsers = () =>
	{
		if (boardToManage)
		{
			request.patch(
	            'board/' + boardToManage.id,
				{
					userIds: boardToManage.userIds,
				},
	            () =>
				{
					getBoards();
					closeManageUsersModal();
					notifications.showSaveUsersSuccess();
	            },
	            (error) => notifications.showSaveUsersError(),
	        );
		}
	}


	const handleBoardTitleInputChange = (event) =>
	{
		setBoardToEdit({
			...boardToEdit,
			title: event.target.value,
		});
	}


	const saveBoard = () =>
	{
		if (boardToEdit)
		{
			request.patch(
	            'board/' + boardToEdit.id,
				{
					title: boardToEdit.title,
				},
	            () =>
				{
					getBoards();
					setBoardToEdit(undefined);
					notifications.showSaveBoardSuccess();
	            },
	            (error) => notifications.showSaveBoardError(),
	        );
		}
	}


	const deleteBoard = (boardToDelete) =>
	{
		if (boardToDelete)
		{
			request.delete(
	            'board/' + boardToDelete.id,
	            () =>
				{
					getBoards();
					notifications.showDeleteBoardSuccess();
	            },
	            (error) => notifications.showDeleteBoardError(),
	        );
		}
	}


	const filterBoards = (searchValue) =>
	{
		setBoards(allBoards.filter((board) =>
			{
				return board.title.includes(searchValue);
			})
		);
	}


	return (

		<BoardListView
			{...props}
			isAdmin={isAdmin}
			boards={boards}
			users={users}
			boardToManage={boardToManage}
			boardsLoading={boardsLoading}
			manageUsersModalIsOpen={manageUsersModalIsOpen}
			openManageUsersModal={openManageUsersModal}
			closeManageUsersModal={closeManageUsersModal}
			handleBoardUsersSelectChange={handleBoardUsersSelectChange}
			saveBoardUsers={saveBoardUsers}
			boardToEdit={boardToEdit}
			setBoardToEdit={setBoardToEdit}
			handleBoardTitleInputChange={handleBoardTitleInputChange}
			saveBoard={saveBoard}
			getBoards={getBoards}
			deleteBoard={deleteBoard}
			filterBoards={filterBoards}
		/>
	);
}


export default Boards;
