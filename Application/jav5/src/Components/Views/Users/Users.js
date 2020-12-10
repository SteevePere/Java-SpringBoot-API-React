import React, { useState, useEffect } from 'react';

import UserListView from './List/UserListView';
import EditUserView from './Edit/EditUserView';

import { notifications } from './Notifications/notifications';

import { auth } from 'Lib/auth';
import { request } from 'Lib/request.js';


const Users = (props) =>
{

	const [users, setUsers] = useState([]);
	const [userToEdit, setUserToEdit] = useState();
	const [drawerVisible, setDrawerVisible] = useState(false);
	const [toggleLoading, setToggleLoading] = useState(false);


	useEffect(() =>
		{
			getUsers();
		},
		[]
	);


	const getUsers = () =>
	{
		request.get(
			'user',
			{},
			(users) => setUsers(users),
			(e) => console.error(e),
		);
	}


	const editUser = (user) =>
	{
		setUserToEdit(user);
		openDrawer();
	}


	const openDrawer = () => setDrawerVisible(true);


	const closeDrawer = () => setDrawerVisible(false);


	const deleteUser = (user) =>
	{
		if (user)
		{
			request.delete(
	            'user/' + user.id,
	            (confirmation) =>
				{
					if (confirmation.success)
					{
						notifications.showDeleteSuccess();
						getUsers();
					}

					else notifications.showDeleteError();
	            },
	            () => notifications.showDeleteError(),
	        );
		}
	}


	const canEdit = (record) => auth.isAdmin() ||
		auth.getUserId() === record.id;


	const canChangeRole = () => auth.isAdmin();


	const canToggle = (record) => auth.isAdmin() &&
		auth.getUserId() !== record.id;


	const canDelete = (record) => auth.isAdmin() &&
		auth.getUserId() !== record.id;


	const canDownload = () => auth.isAdmin();


	const isMe = (user) => user.id === auth.getUserId();


	const getData = () =>
	{
		return users && users.map(user =>
		{
			return user ? {
				key: user.id,
				id: user.id,
				username: user.username,
				role: user.role,
				isActive: user.isActive,
				creationDate: user.creationDate,
				updatedDate: user.updatedDate,
			} : null;
		});
	}


	const handleInputChange = (event, key) =>
	{
		const value = event.target.value;

		setUserToEdit({
			...userToEdit,
			[key]: value === '' ? undefined : value,
		});
	}


	const formIsValid = () =>
	{
		return userToEdit.role &&
			userToEdit.username &&
			(!userToEdit.password ||
				userToEdit.confirmPassword === userToEdit.password);
	}


	const updateUser = () =>
	{
		if (userToEdit && formIsValid())
		{
			request.patch(
	            'user/' + userToEdit.id,
				{
					...userToEdit,
				},
	            () =>
				{
					if (isMe(userToEdit))
					{
						closeDrawer();
						setTimeout(() => auth.signOut(props), 1500);
						notifications.showEditSuccess(
							'Your Account has been successfully updated. '
							+ 'You will now be redirected to Login.'
						);

					}

					else
					{
						closeDrawer();
						getUsers();
						notifications.showEditSuccess(
							'This User has been successfully updated.'
						);
					}
	            },
	            (error) =>
				{
					if (error.status === 409)
						notifications.showEditError('This Username is already taken.');

					else notifications.showEditError();
				},
	        );
		}

		else notifications.showInvalidFormError();
	}


	const toggleUser = (user) =>
	{
		setToggleLoading(true);

		if (user)
		{
			request.patch(
	            'user/activate/' + user.id,
				{
					isActive: !user.isActive,
				},
	            (user) =>
				{
					if (user.isActive)
						notifications.showUserActivated();

					else notifications.showUserDeactivated();

					getUsers();
	            },
	            () => notifications.showToggleError(),
				() => setToggleLoading(false)
	        );
		}
	}


	const downloadUsers = () =>
	{
		request.jsonExport(
			'user/export',
			'users',
			() => {},
			() => notifications.showDownloadError(),
		);
	}


	return (

		<>
			<UserListView
				{...props}
				data={getData()}
				canToggle={canToggle}
				canEdit={canEdit}
				canDelete={canDelete}
				canDownload={canDownload}
				isMe={isMe}
				toggleUser={toggleUser}
				editUser={editUser}
				deleteUser={deleteUser}
				downloadUsers={downloadUsers}
				toggleLoading={toggleLoading}
			/>
			<EditUserView
				{...props}
				userToEdit={userToEdit}
				isMe={isMe}
				canChangeRole={canChangeRole}
				drawerVisible={drawerVisible}
				setDrawerVisible={setDrawerVisible}
				handleInputChange={handleInputChange}
				updateUser={updateUser}
			/>
		</>
	);
}


export default Users;
