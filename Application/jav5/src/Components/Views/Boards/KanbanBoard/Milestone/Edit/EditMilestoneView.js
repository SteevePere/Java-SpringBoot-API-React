import React from 'react';
import { Tooltip } from 'antd';

import {
	EditOutlined,
} from '@ant-design/icons';

import AddEditMilestoneModal from '../Form/AddEditMilestoneModal';


const EditMilestoneView = (props) =>
{

	const {
		openModal,
	} = props;


	return (

		<>
			<Tooltip title='Edit Milestone' placement='bottom'>
				<EditOutlined
					key='edit'
					onClick={openModal}
				/>
			</Tooltip>
			<AddEditMilestoneModal
				{...props}
				title='Edit Milestone'
			/>
		</>
	);
}


export default EditMilestoneView;
