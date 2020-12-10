import React from 'react';
import { Modal, Space, Card } from 'antd';
import {
	AimOutlined,
} from '@ant-design/icons';

import AddEditMilestoneFormView from './AddEditMilestoneFormView';


const AddEditMilestoneModal = (props) =>
{

	const {
		title,
		closeModal,
		modalVisible,
		saveMilestone,
	} = props;


	return (

		<Modal
			title={<Space><AimOutlined/> {title}</Space>}
			visible={modalVisible}
			onOk={saveMilestone}
			onCancel={closeModal}
			width={800}
		>
			<Card>
				<AddEditMilestoneFormView
					{...props}
				/>
			</Card>
		</Modal>
	);
}


export default AddEditMilestoneModal;
