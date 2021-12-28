import re
import logging
from datetime import datetime,timedelta
logger = logging.getLogger(__name__)

numbers = {"하루": 1,
           "이틀": 2,
           "사흘": 3,
           "나흘": 4,
           "닷새": 5,
           "엿새": 6,
           "이레": 7,
           "여드레": 8,
           "아흐레": 9,
           "열흘": 10,
           "열하루": 11,
           "보름": 15,
           "스무날": 20
           }


def todaytomrrowyesterday(raw):
    '''
    한글로 되어 있는 날짜에서 숫자로 월, 일 정보 반환
    '''
    today= ""

    if '오늘' == raw:
        today=datetime.today().strftime("%Y%m%d")
        
    elif '어제' == raw:
        today = datetime.today() - timedelta(1)
        today = today.strftime("%Y%m%d")
  
    elif '내일' == raw:
        today = datetime.today() + timedelta(1)
        today = today.strftime("%Y%m%d")
    
    elif '모레' == raw:
        today = datetime.today() + timedelta(2)
        today = today.strftime("%Y%m%d")
   
    return today


def extractIntegersFromRawMixDate(rawDate, today, book=True):
    nums = re.findall('[1-9][0-9][0-9][0-9]|[0-9]?[0-9]', rawDate)
    if len(nums) == 3: # year, month, day
        if len(nums[0]) == 4:
            year = int(nums[0])
        elif len(nums[0]) == 2:

            year = int(nums) + 2000
        else:
            raise ValueError('extractIntegersFromRawMixDate : wrong format {}'.format(rawDate))
        month = int(nums[1])
        day = int(nums[2])

    elif len(nums) == 2:
        year = today.year
        month = int(nums[0])
        day = int(nums[1])

    elif len(nums) == 1:
        year = today.year
        month = today.month
        day = int(nums[0])
    else:
        raise ValueError('extractIntegersFromRawMixDate : wrong format {}'.format(rawDate))

    return datetime(year, month, day)
def convertRawToDate(rawData):
    '''
    전체가 한글로 표현되거나 일부만 한글로 되어 있는 날짜를
    YYYY-MM-dd 형식으로 변환하여 반환
    '''
    condition_i = '((20)?[0-2][0-9]년 ?)?(1?[0-9]월 ?)?[1-3]?[0-9]일'
    today = datetime.now()

    if type(rawData) is not list:
        rawData = [rawData]

    wrong = True
    for rawDate in rawData:
        if(re.search(condition_i, rawDate)):
            date = extractIntegersFromRawMixDate(rawDate, today)
            wrong = False
            break
    if wrong:
        raise ValueError('Wrong Date Format : 확인불가 유형')
    return date

