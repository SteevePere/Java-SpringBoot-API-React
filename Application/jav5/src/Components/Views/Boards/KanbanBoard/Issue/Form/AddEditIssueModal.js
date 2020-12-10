import React from 'react';
import { Modal, Space, Card } from 'antd';
import {
	BugOutlined,
} from '@ant-design/icons';

import AddEditIssueFormView from './AddEditIssueFormView';


const AddEditIssueModal = (props) =>
{

	const {
		title,
		closeModal,
		modalVisible,
		saveIssue,
	} = props;


	return (

		<Modal
			title={<Space><BugOutlined/> {title}</Space>}
			visible={modalVisible}
			onOk={saveIssue}
			onCancel={closeModal}
			width={800}
		>
			<Card>
				<AddEditIssueFormView
					{...props}
				/>
			</Card>
		</Modal>
	);
}


export default AddEditIssueModal;
