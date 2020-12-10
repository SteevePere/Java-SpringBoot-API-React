import React, { useState, useEffect } from 'react';

import AddBoardView from './AddBoardView';

import { notifications } from '../Notifications/notifications';

import { request } from 'Lib/request';


const AddBoard = (props) =>
{

	const {
		getBoards,
	} = props;

	const [modalVisible, setModalVisible] = useState(false);
	const [boardToCreate, setBoardToCreate] = useState({});


	useEffect(() =>
		{
			if (!modalVisible)
			{
				setBoardToCreate({});
			}
		},
		[modalVisible]
	);


	const openModal = () => setModalVisible(true);


	const closeModal = () => setModalVisible(false);


	const formIsValid = () => boardToCreate.title !== undefined &&
		boardToCreate.title !== '';


	const handleInputChange = (event, key) =>
	{
		const value = event.target.value;

		setBoardToCreate({
			...boardToCreate,
			[key]: value,
		});
	}


	const createBoard = () =>
	{
		if (formIsValid())
		{
			request.post(
	            'board',
	            boardToCreate,
	            (response) =>
				{
					notifications.showCreateBoardSuccess();
					closeModal();
					getBoards();
	            },
	            () => notifications.showCreateBoardError(),
				() => {},
	        );
		}

		else notifications.showInvalidFormError();
	}


	return (

		<AddBoardView
			{...props}
			board={boardToCreate}
			openModal={openModal}
			closeModal={closeModal}
			modalVisible={modalVisible}
			handleInputChange={handleInputChange}
			createBoard={createBoard}
		/>
	);
}


export default AddBoard;
