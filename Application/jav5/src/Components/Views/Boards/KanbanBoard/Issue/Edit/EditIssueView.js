import React from 'react';
import { Tooltip } from 'antd';

import {
	EditOutlined,
} from '@ant-design/icons';

import AddEditIssueModal from '../Form/AddEditIssueModal';


const EditIssueView = (props) =>
{

	const {
		openModal,
	} = props;


	return (

		<>
			<Tooltip title='Edit Issue' placement='bottom'>
				<EditOutlined
					key='edit'
					onClick={openModal}
				/>
			</Tooltip>
			<AddEditIssueModal
				{...props}
				title='Edit Issue'
			/>
		</>
	);
}


export default EditIssueView;
