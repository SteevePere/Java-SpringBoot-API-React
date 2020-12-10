import React from 'react';
import { Table, Tag, Button, Space, Popconfirm, Tooltip } from 'antd';
import {
	EditTwoTone,
	DeleteTwoTone,
	CheckCircleTwoTone,
	CloseCircleTwoTone,
} from '@ant-design/icons';
import moment from 'moment';

import ExportButton from 'Components/Lib/ExportButton/ExportButton';

import { RolesModel } from 'Models/RolesModel';


const UserListView = (props) =>
{

	const {
		data,
		canToggle,
		canEdit,
		canDelete,
		canDownload,
		isMe,
		toggleUser,
		editUser,
		deleteUser,
		downloadUsers,
		toggleLoading,
	} = props;


	const rolesDisplay = {
		[RolesModel.ADMIN]: {
			name: 'ADMIN',
			color: 'red',
		},
		[RolesModel.USER]: {
			name: 'USER',
			color: 'green',
		},
	};


	const columns = [
		{
			title: 'ID',
			dataIndex: 'id',
			key: 'id',
		},
		{
			title: 'Username',
			dataIndex: 'username',
			key: 'username',
		},
		{
			title: 'Role',
			key: 'role',
			dataIndex: 'role',
			render: role => (
				<Tag
					color={rolesDisplay[role].color}
					key={role}
					style={{ width: 80, textAlign: 'center' }}
				>
					{rolesDisplay[role].name}
				</Tag>
			),
		},
		{
			title: 'Account Status',
			key: 'isActive',
			dataIndex: 'isActive',
			render: isActive => (
				<Tag
					color={isActive ? 'green' : 'red'}
					key={isActive}
					style={{ width: 80, textAlign: 'center' }}
				>
					{isActive ? 'ACTIVE' : 'INACTIVE'}
				</Tag>
			),
		},
		{
			title: 'Actions',
			key: 'actions',
			render: (text, record) => (
				<Space size='small'>
					{canEdit(record) &&
					<Tooltip title='Edit'>
						<Button
							shape='circle'
							icon={<EditTwoTone twoToneColor='#52c41a'/>}
							onClick={() => editUser(record)}
						/>
					</Tooltip>}
					{canToggle(record) &&
					<Tooltip title='Toggle Access'>
						<Button
							shape='circle'
							icon={record.isActive ?
								<CloseCircleTwoTone twoToneColor='orange'/> :
								<CheckCircleTwoTone twoToneColor='#52c41a'/>}
							onClick={() => toggleUser(record)}
							loading={toggleLoading}
						/>
					</Tooltip>}
					{canDelete(record) &&
					<Tooltip title='Delete'>
						<Popconfirm
							placement='right'
							title='Are you sure you want to delete this User?'
							onConfirm={() => deleteUser(record)}
							okText='Yes'
							cancelText='No'
						>
							<Button
								shape='circle'
								icon={<DeleteTwoTone twoToneColor='#eb2f96'/>}
							/>
						</Popconfirm>
					</Tooltip>}
				</Space>
			),
		},
	];


	const getUserDetails = (user) =>
	{
		return (
			<>
				<li>
					<i>
						Created on {moment(user.creationDate).format('YYYY-MM-DD')}
					</i>
				</li>
				<li>
					<i>
						Last updated on {moment(user.updatedDate).format('YYYY-MM-DD')}
					</i>
				</li>
			</>
		);
	};


	return (

		<>
			<Table
				columns={columns}
				dataSource={data}
				expandable={{
					expandedRowRender: user => getUserDetails(user),
				}}
				rowClassName={
					(record) => isMe(record) ? 'table-row-dark' : ''
				}
			/>
			{canDownload() &&
			<ExportButton
				download={downloadUsers}
			/>}
		</>
	);
}


export default UserListView;
