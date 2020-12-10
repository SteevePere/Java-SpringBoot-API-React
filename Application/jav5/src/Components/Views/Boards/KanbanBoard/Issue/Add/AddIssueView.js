import React from 'react';
import { Button } from 'antd';
import {
	PlusOutlined,
} from '@ant-design/icons';

import AddEditIssueModal from '../Form/AddEditIssueModal';


const AddIssueView = (props) =>
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
				New Issue
			</Button>
			<AddEditIssueModal
				{...props}
				title='Add Issue'
			/>
		</>
	);
}


export default AddIssueView;
