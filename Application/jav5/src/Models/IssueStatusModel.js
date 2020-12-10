import React from 'react';
import {
	FolderOpenOutlined,
	SyncOutlined,
	SearchOutlined,
	DeploymentUnitOutlined,
	IssuesCloseOutlined,
} from '@ant-design/icons';


export const IssueStatusModel = {
	OPEN: {
		value: 'OPEN',
		label: 'Open',
		name: 'Open',
		chartColor: '#87d068',
		titleTagColor: '#87d068',
		icon: <FolderOpenOutlined/>,
		items: [],
	},
	IN_PROGRESS: {
		value: 'IN_PROGRESS',
		label: 'In Progress',
		name: 'In Progress',
		chartColor: '#fa8c16',
		titleTagColor: '#fa8c16',
		icon: <SyncOutlined/>,
		items: [],
	},
	TESTING: {
		value: 'TESTING',
		label: 'Testing',
		name: 'Testing',
		chartColor: '#2db7f5',
		titleTagColor: '#2db7f5',
		icon: <SearchOutlined/>,
		items: [],
	},
	DEPLOYED: {
		value: 'DEPLOYED',
		label: 'Deployed',
		name: 'Deployed',
		chartColor: '#108ee9',
		titleTagColor: '#108ee9',
		icon: <DeploymentUnitOutlined/>,
		items: [],
	},
	CLOSED: {
		value: 'CLOSED',
		label: 'Closed',
		name: 'Closed',
		chartColor: '#722ed1',
		titleTagColor: '#722ed1',
		icon: <IssuesCloseOutlined/>,
		items: [],
	},
};
