import React from 'react';
import { Button, Tooltip } from 'antd';
import {
	CloudDownloadOutlined,
} from '@ant-design/icons';

import './ExportButton.css';


const ExportButton = (props) =>
{

	const {
		download,
	} = props;


	return (

		<Tooltip title='Export Data'>
			<Button
				className='export_button'
				type='primary'
				shape='circle'
				size='large'
				onClick={download}
			>
				<CloudDownloadOutlined/>
			</Button>
		</Tooltip>
	);
}


export default ExportButton;
