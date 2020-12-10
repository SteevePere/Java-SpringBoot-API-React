import React from 'react';
import { Button } from 'antd';
import {
	PlusOutlined,
} from '@ant-design/icons';

import AddEditMilestoneModal from '../Form/AddEditMilestoneModal';


const AddMilestoneView = (props) =>
{

	const {
		openModal,
	} = props;


	return (

		<>
			<Button
				onClick={openModal}
				type='primary'
			>
				<PlusOutlined/>
				New Milestone
			</Button>
			<AddEditMilestoneModal
				{...props}
				title='Add Milestone'
			/>
		</>
	);
}


export default AddMilestoneView;
