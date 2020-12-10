import React, { useState } from 'react';

import ViewIssueView from './ViewIssueView';


const ViewIssue = (props) =>
{

	const [modalVisible, setModalVisible] = useState(false);


	const openModal = () => setModalVisible(true);


	const closeModal = () => setModalVisible(false);


	return (

		<ViewIssueView
			{...props}
			openModal={openModal}
			closeModal={closeModal}
			modalVisible={modalVisible}
		/>
	);
}


export default ViewIssue;
