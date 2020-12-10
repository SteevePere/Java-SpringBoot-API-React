import { utils } from 'Lib/utils';


export const notifications =
{

	showDownloadError()
	{
		utils.openNotification(
			'error',
			'Download Error',
			'An error occurred, could not retrieve file.',
			'bottomRight',
			3
		);
	},
};
