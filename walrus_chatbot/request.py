from enum import Flag
import requests
from collections import Counter
import json
from ast import literal_eval
import operator

portuseurl="http://13.125.187.57:3001/portuse?PrtAgCd=%s&PrtAgNm=%s&Clsgn=%s&vsslNm=%s&BeginDt=%s&EndDt=%s"
inouturl="http://13.125.187.57:3001/inout?PrtAgCd=%s&PrtAgNm=%s&Clsgn=%s&vsslNm=%s&BeginDt=%s&EndDt=%s"

def reqPortuse(PrtAgCd: str,PrtAgNm: str,Clsgn: str,vsslNm: str,BeginDt: str,EndDt: str):
        url=portuseurl%(PrtAgCd,PrtAgNm,Clsgn,vsslNm,BeginDt,EndDt)
        data=requests.get(url).json()
        return data

def reqInout(PrtAgCd: str,PrtAgNm: str,Clsgn: str,vsslNm: str,BeginDt: str,EndDt: str):
        url=inouturl%(PrtAgCd,PrtAgNm,Clsgn,vsslNm,BeginDt,EndDt)
        data=requests.get(url).json()
        return data

def findInfo(data:list,info:str):
    if(data[0]==[]):
        return 'None'
    length=int(data[0][0].get('totalCount'))
    list=[]
    for i in range(0,length):
        list.append(data[0][i].get(info))
    return list



def ChartData(data: list):
    in_cnt = 0
    out_cnt = 0
    
    kind_dict={}
    country_dict={}
    length = data[0][0].get('totalCount')
    for i in range(0,length):
        if(data[0][i].get('etryndNm')=='입항'):
            in_cnt+=1
        else:
            out_cnt += 1        
        if(data[0][i].get('vsslKindNm') in kind_dict.keys()):
            tmp = kind_dict[data[0][i].get('vsslKindNm')]
            tmp +=1
            kind_dict[data[0][i].get('vsslKindNm')] = tmp     
        else:
            kind_dict[data[0][i].get('vsslKindNm')] = 1        
        if(data[0][i].get('vsslNltyNm') in country_dict.keys()):
            tmp = country_dict[data[0][i].get('vsslNltyNm')]
            tmp +=1
            country_dict[data[0][i].get('vsslNltyNm')] = tmp
        else:
            country_dict[data[0][i].get('vsslNltyNm')] = 1
    
    kind_dict = sorted(kind_dict.items(),key=operator.itemgetter(1),reverse=True)
    kind_dict_result={}
    kind_etc_cnt=0
    
    if(len(kind_dict)>5):
        for i in range(4,len(kind_dict)):
            kind_etc_cnt += kind_dict[i][1]
        for j in range(0,4):
            kind_dict_result[kind_dict[j][0]] = kind_dict[j][1]
        kind_dict_result['기타'] = kind_etc_cnt
    else:
        for i in range(0,5):
            kind_dict_result[kind_dict[i][0]] = kind_dict[i][1]            
            
        
      
    
    country_dict = sorted(country_dict.items(),key=operator.itemgetter(1),reverse=True)
    country_dict_result={}
    country_etc_cnt=0

    if(len(country_dict)>5):
        for i in range(4,len(country_dict)):
            country_etc_cnt += country_dict[i][1]
        for j in range(0,4):
            country_dict_result[country_dict[j][0]] = country_dict[j][1]
        country_dict_result['기타'] = country_etc_cnt        
    else: 
        for i in range(0,5):
            country_dict_result[country_dict[i][0]] = country_dict[i][1]


    return_dict = {'inout':{'입항': in_cnt,'출항':out_cnt},
                   'kind': kind_dict_result,
                   'country': country_dict_result}
    json_data = json.dumps(return_dict,ensure_ascii=False)
    
    return json_data    


def ChartDataPort(data: list):
    permission_cnt = 0
    unpermission_cnt = 0
    else_cnt = 0
    hun_cnt = 0
    tho_cnt = 0
    ttho_cnt = 0
    htho_cnt = 0
    millon_cnt = 0

    
    facility_dict={}
    length = data[0][0].get('totalCount')

    for i in range(0,length):
        if(data[0][i].get('prmisnYn')=='허가완료'):
            permission_cnt+=1
        elif(data[0][i].get('prmisnYn')=='미허가'):
            unpermission_cnt += 1
        else:
            else_cnt+= 1

        if(data[0][i].get('reqstFcltyNm') in facility_dict.keys()):
            tmp = facility_dict[data[0][i].get('reqstFcltyNm')]
            tmp +=1
            facility_dict[data[0][i].get('reqstFcltyNm')] = tmp     
        else:
            facility_dict[data[0][i].get('reqstFcltyNm')] = 1        

        if(int(data[0][i].get('realTn')) > 1000000):
            millon_cnt +=1
        elif(int(data[0][i].get('realTn'))<1000000 and int(data[0][i].get('realTn'))>100000):
            htho_cnt +=1
        elif(int(data[0][i].get('realTn')) <100000 and int(data[0][i].get('realTn'))>10000):
            ttho_cnt += 1
        elif(int(data[0][i].get('realTn')) <10000 and int(data[0][i].get('realTn'))>1000):
            tho_cnt    
        elif(int(data[0][i].get('realTn'))<1000):
            hun_cnt += 1
    
    facility_dict = sorted(facility_dict.items(),key=operator.itemgetter(1),reverse=True)
    facility_dict_result={}
    facility_etc_cnt=0
    
    if(len(facility_dict)>5):
        for i in range(4,len(facility_dict)):
            facility_etc_cnt += facility_dict[i][1]
        for j in range(0,4):
            facility_dict_result[facility_dict[j][0]] = facility_dict[j][1]
        facility_dict_result['기타'] = facility_etc_cnt
    else:
        for i in range(0,5):
            facility_dict_result[facility_dict[i][0]] = facility_dict[i][1]              







    return_dict = {'permission':{'허가완료': permission_cnt,'미허가':unpermission_cnt,'미정':else_cnt},
                   'location': facility_dict_result,
                   'ton': {'백 톤급':hun_cnt,'천 톱급':tho_cnt,'만 톤급':ttho_cnt,'십만 톤급':htho_cnt,'백만 톱급':millon_cnt} 
                   }

    json_data = json.dumps(return_dict,ensure_ascii= False)
    return json_data    


def replaceRight(original, old, new, count_right):
    repeat=0
    text = original
    old_len = len(old)
    
    count_find = original.count(old)
    if count_right > count_find : # 바꿀 횟수가 문자열에 포함된 old보다 많다면
        repeat = count_find # 문자열에 포함된 old의 모든 개수(count_find)만큼 교체한다
    else :
        repeat = count_right # 아니라면 입력받은 개수(count)만큼 교체한다

    while(repeat):
      find_index = text.rfind(old) # 오른쪽부터 index를 찾기위해 rfind 사용
      text = text[:find_index] + new + text[find_index+old_len:]

      repeat -= 1
      
    return text

#PrtAgCd: 청코드
#PrtAgNm: 항구이름
#Clsgn: 호출부호
#vsslNm: 선박명
#BeginDt: 검색 시작날짜
#EndDt: 검색 종료날짜


#(example) reqPortuse('030','인천','ABCD','페리호','20210805','20210811') ==> 청코드와 날짜는 필수로 넣어야하고 나머지 매개변수는 넣고싶으면 넣고 안넣고 싶으면 ''로 넣으면 됨
#(exmaple) reqInout('030','인천','ABCD','','20210805','20210811')==> 날짜는 필수로 넣어야하고 나머지 매개변수는 넣고싶으면 넣고 안넣고 싶으면 ''로 넣으면 됨

# data 뽑아올때는 json 으로 전달하지만 python에서는 딕셔너리 형태라 reqPortuse('030','인천','ABCD','페리호','20210805','20210811')[i].get("원하는정보") 로 뽑으면 뽑아올수 있음

if __name__ == "__main__":
    a = reqPortuse("020","","","","20210912","20210912")
    b= ChartDataPort(a)
    
    print(b)
    