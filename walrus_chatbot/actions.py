# This files contains your custom actions which can be used to run
# custom Python code.
#
# See this guide on how to implement these action:
# https://rasa.com/docs/rasa/core/actions/#custom-actions/


# This is a simple example for a custom action which utters "Hello World!"
from typing import Any, Text, Dict, List
from collections import Counter
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
import os
from typing import  Dict, Text, Any, List, Union, Optional
import re
from rasa_sdk import Action, Tracker
from rasa_sdk.forms import FormAction, REQUESTED_SLOT
from rasa_sdk.executor import CollectingDispatcher
import korean2num
import request
from datetime import timedelta, datetime
import requests
import logging
import utils
logger = logging.getLogger(__name__)
from rasa_sdk.events import (
    AllSlotsReset,
    SlotSet,
    EventType,
    ActionExecuted,
    SessionStarted,
    Restarted,
    FollowupAction,
)

def extractIntegersFromRawMixDate(rawDate, today, book=True):
    nums = re.findall('[1-9][0-9][0-9][0-9]|[0-9]?[0-9]', rawDate)
    if len(nums) == 3: # year, month, day
        if len(nums[0]) == 4:
            year = int(nums[0])
        elif len(nums[0]) == 2:
            if book:
                year = int(nums[0])+2000
                if year >= today.year+2:
                    raise ValueError('extractIntegersFromRawMixDate : 예약불가 연도')
            else: # 몇 년생 물어보는 것에 대한 대답 처리
                if int(nums) <= today.year - 2000 + 1:
                    year = int(nums) + 2000
                else:
                    year = int(nums) + 1900
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


def extractIntegerFromHanguelDate(raw, today):
    '''
    한글로 되어 있는 날짜에서 숫자로 월, 일 정보 반환
    '''
    if '년' in raw:
        year = korean2num.decode(raw.split('년')[0])
        raw = raw.split('년')[1].strip()
    else:
        year = today.year
    if '월' in raw:
        tmp = raw.split('월')[0]
        if tmp == '시':
            month = 10
        else:
            month = korean2num.decode(tmp)
        raw = raw.split('월')[1].strip()
    else:
        month = today.month
    if '일' in raw:
        day = korean2num.decode(raw.split('일')[0])
    else:
        raise ValueError('Wrong Date Format')

    # print('reformated date : {}-{}-{}'.format(year, month, day))
    return datetime(year=year, month=month, day=day)

def convertRawToDate(rawData):
    '''
    전체가 한글로 표현되거나 일부만 한글로 되어 있는 날짜를
    YYYY-MM-dd 형식으로 변환하여 반환
    '''
    condition_h = '((십|이십)년 ?)?((일|이|삼|사|오|유|육|칠|팔|구|십|시|십일|십이)월 ?)?((이|삼)?십)?(일|이|삼|사|오|육|칠|팔|구)일|' \
                  '((십|이십)년 ?)?((일|이|삼|사|오|유|육|칠|팔|구|십|시|십일|십이)월 ?)?((이|삼)?십)(일|이|삼|사|오|육|칠|팔|구)?일'
    condition_i = '((20)?[0-2][0-9]년 ?)?(1?[0-9]월 ?)?[1-3]?[0-9]일'
    today = datetime.now()

    if type(rawData) is not list:
        rawData = [rawData]

    wrong = True
    for rawDate in rawData:
        if re.search(condition_h, rawDate):
            date = extractIntegerFromHanguelDate(rawDate, today)
            wrong = False
            break
        elif re.search(condition_i, rawDate):
            date = extractIntegersFromRawMixDate(rawDate, today)
            wrong = False
            break
    if wrong:
        raise ValueError('Wrong Date Format : 확인불가 유형')
    return date


class FacForm(FormAction):

    def name(self) -> Text:
        """Unique identifier of the form"""
        return "fac_form"

    @staticmethod
    def required_slots(tracker: Tracker) -> List[Text]:
        """A list of required slots that the form has to fill"""
        '''
        3. 시작끝날짜 있음
        '''
        return ['date', 'location']

    def slot_mappings(self) -> Dict[Text, Any]:
        return {"date": self.from_entity(entity='date'),
                "location": self.from_entity(entity='location'),
                }

    def submit(self,
               dispatcher: CollectingDispatcher,
               tracker: Tracker,
               domain: Dict[Text, Any]
               ) -> List[Dict]:

        startdate = tracker.get_slot('date')
        start = startdate
        
        if ('어제' == startdate or '오늘' == startdate or '내일' == startdate):
            startdate = utils.todaytomrrowyesterday(startdate)
        else:
            startdate = convertRawToDate(startdate)
            startdate = startdate.strftime('%Y%m%d')

        location = tracker.get_slot('location')

        try:
            answer = request.reqPortuse('', location, '', '', startdate, startdate)
            total = request.findInfo(answer, 'totalCount')[0]
            ton = request.findInfo(answer, 'realTn')
            permit = request.findInfo(answer, 'prmisnYn')
            count_name = request.findInfo(answer, 'satmntEntrpsNm')
            count_location = request.findInfo(answer, 'reqstFcltyNm')
            test = request.ChartDataPort(answer)
            thousand = 0
            man = 0
            obak = 0
            hugawanryo = 0
            mehuga = 0
            count = Counter(count_name)
            count2 = Counter(count_location)
            tag_count = []
            tags = []
            tags_location_count = []
            tags_location = []
            
            if('어제' in start or '오늘' in start or '내일' in start):
                    start = start
            else:
                start = convertRawToDate(start).strftime('%Y-%m-%d')
            

            for i in ton:
                if (i > 10000): 
                    man += 1
                elif (i > 1000):
                    thousand += 1
                else:
                    obak += 1
            
            for j in permit:
                if (j == "허가완료"):
                    hugawanryo += 1
                else:
                    mehuga += 1
            '''
            해운사 이름 빈도수 10개 높은것 
            '''
            for n, c in count.most_common(10):

                dics = {'tag': n, 'count': c}

                if len(dics['tag']) >= 2 and len(tags) <= 49:
                    tag_count.append(dics)
                    tags.append(dics['tag'])
            exercise =''
            for tag in tag_count:
                exercise +=("{} {}\n".format(tag['tag'], tag['count']))
            
            '''
            시설 상위 10개
            '''
            for a, b in count2.most_common(10):
            
                dics2 = {'tag2': a, 'count2': b}

                if len(dics2['tag2']) >= 2 and len(tags_location) <= 49:
                    tags_location_count.append(dics2)
                    tags_location.append(dics2['tag2'])

            exercise2 =''
            for tag2 in tags_location_count:
                exercise2 +=("{} {}\n".format(tag2['tag2'], tag2['count2']))

            dispatcher.utter_message(
                text="😊예보일시가 %s인 %s 항구의 처리현황😊\n 총 %s 개 \n 허가완료 된 선박의 수⚓: %s \n 미허가 선박의 수⚓: %s \n 10000톤급 : %s개  1000톤급 : %s개  100톤급: %s개" % (
                start, location, total, hugawanryo, mehuga, man, thousand, obak))
            dispatcher.utter_message(
                text="🚢허가완료를 신청한 상위 10개의 선박회사🚢 \n해운회사명 허거완료횟수 \n%s" %(exercise))
            dispatcher.utter_message(
                text="✅시설 사용 신청 장소 상위 10개✅\n신청시설 허거완료횟수 \n%s" %(exercise2))
            dispatcher.utter_message(json_message =test)
        except:
            dispatcher.utter_message(text =" 😅시설사용허가현황이 없습니다. 다시 검색해주세요!! 😅")
            return [FollowupAction(name='action_chat_restart')]
            
        return []

class ActionRestarted(Action):

    def name(self):
        return "action_chat_restart"

    def run(self, dispatcher, tracker, domain):
        return [Restarted()]
class ShipForm(FormAction):
    
    def name(self) -> Text:
        return "ship_form"

    @staticmethod
    def required_slots(tracker) -> List[Text]:
        return ['date', 'location']

    def slot_mappings(self) -> Dict[Text, Union[Dict, List[Dict]]]:
        return {
            "location": self.from_entity(entity="location"),
            "date": self.from_entity(entity="date")
        }


    def submit(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List[Dict]:
        startdate = tracker.get_slot("date")
        location = tracker.get_slot("location")
        start = startdate

        if ('어제' in startdate or '오늘' in startdate or '내일' in startdate):
            startdate = utils.todaytomrrowyesterday(startdate)
        else:
            startdate = convertRawToDate(startdate)
            startdate = startdate.strftime('%Y%m%d')


        if('어제' in start or '오늘' in start or '내일' in start):
            start = start
        else:
            start = convertRawToDate(start).strftime('%Y-%m-%d')

        try:
            a=request.reqInout('', location, '', '', startdate, startdate)
            b=request.findInfo(a, 'vsslNm')
            c= ''
            test = request.ChartData(a)

            
            for i in b[0:10]:
                c+=i+'\n'
                

            if(location == '포항신항'):
                location = '포항신'

            
            dispatcher.utter_message(text="⚓%s %s항에는⚓\n%s이(가) 있습니다." % (start, location,c))
            dispatcher.utter_message(json_message =test)
        except:
            dispatcher.utter_message(text =" 😅해당하는 선박정보가 없습니다. 다시 검색해주세요!! 😅")
            return [FollowupAction(name='action_chat_restart')]
        return []


class ResetAllSlot(Action):

    def name(self):
        return "action_reset_all_slot"

    def run(self,
            dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any],
            ) -> List[Dict]:
        return [AllSlotsReset()]

class ShipForm2(FormAction):

    def name(self) -> Text:
        return "ship_form2"

    @staticmethod
    def required_slots(tracker) -> List[Text]:
        return ['shipName']

    def slot_mappings(self) -> Dict[Text, Union[Dict, List[Dict]]]:
        return {
            "shipName": self.from_text()
        }

    def submit(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List[Dict]:
        location = tracker.get_slot("location")
        shipNameFT = tracker.get_slot("shipName")
        startdate = tracker.get_slot("date")

        
        # logger.info('date : {}, format : {}'.format(date, type(date)))
        start = startdate

        if ('어제' in startdate or '오늘' in startdate or '내일' in startdate):
            startdate = utils.todaytomrrowyesterday(startdate)
        else:
            startdate = convertRawToDate(startdate)
            startdate = startdate.strftime('%Y%m%d')
        try:
            a = request.reqInout('',location, '', shipNameFT, startdate,startdate)
            b = request.findInfo(a, 'prtAgNm')
            if('어제' in start or '오늘' in start or '내일' in start):
                    start = start
            else:
                start = convertRawToDate(start).strftime('%Y-%m-%d')

            dispatcher.utter_message(text="🚢%s %s의 정보🚢\n항명: %s\n호출부호: %s\n선명: %s\n외내: %s\n입출: %s\n총톤수: %s\n"
                                        "입항일시: %s\n출항일시: %s\n국적: %s\n선박용도: %s" % (start, shipNameFT,request.findInfo(a, 'prtAgNm')[0],
                                        request.findInfo(a, 'clsgn')[0],request.findInfo(a, 'vsslNm')[0],request.findInfo(a, 'ibobprtSeNm')[0]
                                        ,request.findInfo(a, 'etryndNm')[0],request.findInfo(a, 'grtg')[0],request.findInfo(a, 'etryptDt')[0],request.findInfo(a, 'tkoffDt')[0]
                                        ,request.findInfo(a, 'vsslNltyNm')[0],request.findInfo(a, 'vsslKindNm')[0]))
        except:
            dispatcher.utter_message(text =" 😅해당하는 선박정보가 없습니다. 다시 검색해주세요!! 😅") 

        return []
