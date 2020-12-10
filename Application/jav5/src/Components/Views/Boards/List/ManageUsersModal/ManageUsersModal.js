import React from 'react';
import { Modal, Select } from 'antd';

const { Option } = Select;


const ManageUsersModal = (props) =>
{

	const {
		boardToManage,
		users,
		manageUsersModalIsOpen,
		saveBoardUsers,
		closeManageUsersModal,
		handleBoardUsersSelectChange,
	} = props;


	return (

		<Modal
			title={'Assign Users to Board "' + boardToManage.title + '"'}
			visible={manageUsersModalIsOpen}
			onOk={saveBoardUsers}
			onCancel={closeManageUsersModal}
		>
			<Select
				mode='multiple'
				showSearch
				showArrow
				allowClear
				placeholder='Select Users'
				style={{ width: '100%' }}
				value={boardToManage.userIds}
				onChange={handleBoardUsersSelectChange}
			>
				{users && users.map(user =>
					<Option value={user.id}>{user.username}</Option>
				)}
			</Select>
		</Modal>
	);
}


export default ManageUsersModal;
