% ali kaan biber
% 2018400069
% compiling: yes
% complete: yes


% include the knowledge base
:- ['load.pro'].
% é letter causes problems if this line is not added
:- encoding(utf8).

% 3.1 glanian_distance(Name1, Name2, Distance) 5 points

% number to be ignored is -1
ignoreNum(-1).

% find Name1's expectation list and Name2's feature list and call glanian_distance_cal to calculate distance.
glanian_distance(Name1,Name2,Distance) :- expects(Name1,_,List1), glanian(Name2,_,List2), glanian_distance_cal(List1,List2,D1), Distance is D1^(1/2).

glanian_distance_cal([],[],0).	% base case
glanian_distance_cal([H1|T1],[_|T2],Distance) :- ignoreNum(H1), glanian_distance_cal(T1,T2,Distance), !.		%if H1 is -1 move on to the next feature
glanian_distance_cal([H1|T1],[H2|T2],Distance) :- glanian_distance_cal(T1,T2,D1), Distance is (H1-H2)^2+D1.		%do the necessary calculations

% 3.2 weighted_glanian_distance(Name1, Name2, Distance) 10 points

%same procedure as above, this time involving weigths aswell
weighted_glanian_distance(Name1, Name2, Distance) :- 	expects(Name1,_,List1), glanian(Name2,_,List2), 
														weight(Name1,List3), weighted_glanian_distance_cal(List1,List2,List3,D1), Distance is D1^(1/2).

weighted_glanian_distance_cal([],[],[],0).
weighted_glanian_distance_cal([H1|T1], [_| T2], [_|T3], Distance) :- ignoreNum(H1), weighted_glanian_distance_cal(T1,T2,T3,Distance), !.
weighted_glanian_distance_cal([H1|T1], [H2|T2], [H3|T3], Distance) :- weighted_glanian_distance_cal(T1,T2,T3,D1), Distance is (H3*(H1-H2)^2)+D1.


% 3.3 find_possible_cities(Name, CityList) 5 points

find_possible_cities(Name,CityList) :- city(City,HabitantList,_), member(Name,HabitantList), 				% find the city Name lives 
										likes(Name,_,LikedCities), append([City],LikedCities,CityListT), 	% append that city to Name's LikedCities list
										list_to_set(CityListT,CityList).									% CityList should be a set


% 3.4 merge_possible_cities(Name1, Name2, MergedCities) 5 points


merge_possible_cities(Name1, Name2, MergedCities) :- find_possible_cities(Name1,Cities1), find_possible_cities(Name2,Cities2), 	% find the lists to be merged first by using predicate 3.3
														union(Cities1,Cities2,MergedCities).							% built-in union method the take the union of two lists


% 3.5 find_mutual_activities(Name1, Name2, MutualActivities) 5 points

find_mutual_activities(Name1, Name2, MutualActivities) :- likes(Name1,LikedActs1,_), likes(Name2,LikedActs2,_), 	% find the liked activities for both glanians
															intersection(LikedActs1,LikedActs2,MutualActivities).	% find the intersection of two activity lists 
															

% 3.6 find_possible_targets(Name, Distances, TargetList) 10 points


find_possible_targets(Name, Distances, TargetList) :- 	 findall( [TargetName,Distance] ,(		% aim is to find possible targets and the distance

															expects(Name,GenderList,_), 	 	% expected genders for Name
															glanian(TargetName,Gender,_), 	 	% get the gender of a potential target
															member(Gender,GenderList),			% if target's gender is in expected gender we found a target
															
															glanian_distance(Name,TargetName,Distance)	%calculate the distance 
															) ,Temp),						%put all possible target-distance pairs in a list called Temp 	
															
															sort(2, <, Temp, Sorted),		% sort the list based on distance values
															
															%split the name-distance pairs
															findall(Names, member([Names,_],Sorted),TargetList),	
															findall(Dist, member([_,Dist],Sorted),Distances). 
														


% 3.7 find_weighted_targets(Name, Distances, TargetList) 15 points


find_weighted_targets(Name, Distances, TargetList) :- findall( [TargetName,Distance] ,(		%almost the same as 3.6 but with weighted distances

														expects(Name,GenderList,_), 
														glanian(TargetName,Gender,_), 
														member(Gender,GenderList),
														
														weighted_glanian_distance(Name,TargetName,Distance)
														) ,Temp),
														
														sort(2, <, Temp, Sorted),
														findall(Names, member([Names,_],Sorted),TargetList),
														findall(Dist, member([_,Dist],Sorted),Distances). 


% 3.8 find_my_best_target(Name, Distances, Activities, Cities, Targets) 20 points

find_my_best_target(Name, Distances, Activities, Cities, Targets) :- find_weighted_targets(Name,_,PossibleTargets), 		%get the possible targets by using 3.7
																															%this way we already covered the predicate 6
																		
																		% this part we find the necessary lists which will be used in findall/3 predicate
																		likes(Name,LikedActs,_), 							% get the activities name like
																		dislikes(Name,DislikedActs,DislikedCities,Limits), 	% get the dislikes and limits of name
																		find_possible_cities(Name,CityList),				% predicate 3.3 is useful for checking cities
																		
																		findall( [TargetName,Distance,Activity,City] ,(		% aim is to find all wanted lists
																		
																		% Target predicates
																		% before checking city and activity predicates, checking target suitability is more effective
																		
																		member(TargetName, PossibleTargets),		% target should be in the PossibleTargets list found above
																		
																		% predicate 1
																		% check if name and target have an old relation
																		
																		not(old_relation([Name, TargetName])),
																		not(old_relation([TargetName, Name])),
																		
																		% predicate 7
																		% get the features of target and compare it with Name's tolerances
																		
																		glanian(TargetName,_,GlanianFeatures),
																		toleranceChecker(Limits,GlanianFeatures),
																		
																		% predicate 8
																		% find the target's liked activities and find the intersection with Name's disliked activities

																		likes(TargetName,TargetLikedActs,_),
																		intersection(TargetLikedActs,DislikedActs,ListToCheck),
																		(length(ListToCheck,0);length(ListToCheck,1);length(ListToCheck,2)),
																		
																		% City predicates
																		
																		% predicate 5
																		% choose a city in merged possible cities
																		
																		merge_possible_cities(Name,TargetName,MergedCities),
																		member(City,MergedCities),
																		
																		city(City,_,ActList),			% activities that can be done in a particular city
																		member(Activity,ActList),		% a particular activity for further predicates
																		
																		% predicate 4
																		
																		not(member(City,DislikedCities)),
																		
																		% predicate 2
																		% city is either in CityList which is the result of find_possible_cities predicate
																		% or there should be an activity in that city Name likes
																		
																		(member(City,CityList);member(Activity,LikedActs)),
																		
																		% Activity predicates
																		
																		% predicate 3
																		
																		not(member(Activity,DislikedActs)),
																		
																		weighted_glanian_distance(Name,TargetName,Distance)		% if a target is suitable find the distance
																		
																		), Temp ),	% Temp is the result list
																		
																		% since we choose targets from predicate 3.7 distances are already sorted
																		sort(2,=<,Temp,SortedList),		% sort the list based on activities
																										
																		list_to_set(SortedList,Set),	% in case of duplicates turn the list to a set
																		
																		% split the list into four asked lists
																		findall(TargetName, member([TargetName,_,_,_],Set),Targets),
																		findall(Distance,member([_,Distance,_,_],Set),Distances),
																		findall(Activity,member([_,_,Activity,_],Set),Activities),
																		findall(City,member([_,_,_,City],Set),Cities).


toleranceChecker([],[]).		% base case
toleranceChecker([[]|T1],[_|T2]) :- toleranceChecker(T1,T2).	% if there is no tolerance limit for a feature move on to the next one
toleranceChecker([[A,B]| T1], [H2|T2]) :- A < H2, B > H2, toleranceChecker(T1,T2).	% check the limits and move on to the next one


% 3.9 find_my_best_match(Name, Distances, Activities, Cities, Targets) 25 points

find_my_best_match(Name, Distances, Activities, Cities, Targets) :- find_weighted_targets(Name,_,PossibleTargets), 	
																		% just like the 3.8 but this time taking target's preferences into account

																		% necessary lists which will be used in findall/3
																		likes(Name,LikedActs,_), 
																		dislikes(Name,DislikedActs,DislikedCities,Limits), 
																		find_possible_cities(Name,CityList),
																		glanian(Name,NameGender,NameGlanianFeatures),		% addition to 3.8
																		
																		findall( [TargetName,Distance,Activity,City] ,(
																		
																		% Target predicates
																		
																		member(TargetName, PossibleTargets),
																		dislikes(TargetName,DislikedActsTarget,DislikedCitiesTarget,LimitsTarget), %addition to 3.8
																		
																		% predicate 1
																		% check old relation status
																		
																		not(old_relation([Name, TargetName])),
																		not(old_relation([TargetName, Name])),
																		
																		% predicate 9
																		% get the features of target and compare it to Name's tolerances
																		
																		glanian(TargetName,_,GlanianFeatures),
																		toleranceChecker(Limits,GlanianFeatures),
																		
																		% predicate 10
																		% this time compare features of Name to target's tolerances 
																		
																		toleranceChecker(LimitsTarget,NameGlanianFeatures),
																		
																		% predicate 8
																		% find the expected genders of targets check the suitability
																		
																		expects(TargetName,ExpectedGenders,_),
																		member(NameGender,ExpectedGenders),
																		
																		% predicate 11
																		% find the target's liked activities and find the intersection with Name's disliked activities
																		
																		likes(TargetName,TargetLikedActs,_),
																		intersection(TargetLikedActs,DislikedActs,ListToCheck),
																		(length(ListToCheck,0);length(ListToCheck,1);length(ListToCheck,2)),
																		
																		% predicate 12
																		% find the Name's liked activities and find the intersection with target's disliked activities
																		
																		intersection(LikedActs,DislikedActsTarget,ListToCheck2),
																		(length(ListToCheck2,0);length(ListToCheck2,1);length(ListToCheck2,2)),
																		
																		% City predicates
																		
																		% predicate 6
																		% choose a city in merged possible cities
																		
																		merge_possible_cities(Name,TargetName,MergedCities),
																		member(City,MergedCities),
																		
																		city(City,_,ActList),				% activities that can be done in a particular city
																		member(Activity,ActList),			% a particular activity for further predicates
																		
																		% predicate 5
																		
																		not(member(City,DislikedCities)),
																		not(member(City,DislikedCitiesTarget)),
																		
																		% predicate 2
																		% city is either in CityList which is the result of find_possible_cities predicate
																		% or there should be an activity in that city Name likes
																		
																		(member(City,CityList);member(Activity,LikedActs)),
																		
																		% predicate 3
																		% same as predicate 2 but this considering target's preferences
																		
																		find_possible_cities(TargetName,TargetPossibleCities),
																		(member(City,TargetPossibleCities); member(Activity,TargetLikedActs)),
																		
																		% Activity predicates
																		% predicate 4
																		
																		not(member(Activity,DislikedActs)),
																		not(member(Activity,DislikedActsTarget)),
																		
																		% if target and Name are suitable to each other calculate the distances
																		weighted_glanian_distance(Name,TargetName,Distance1),
																		weighted_glanian_distance(TargetName,Name,Distance2),
																		Distance is (Distance1+Distance2)/2
																		
																		), Temp ),	% result list		
																		
																		% since we choose targets from predicate 3.7 distances are already sorted
																		sort(2,=<,Temp,SortedList),		% sort the list based on activities
																		
																		list_to_set(SortedList,Set),	% get rid of duplicates if any
																		
																		% split the list into four asked lists
																		findall(TargetName, member([TargetName,_,_,_],Set),Targets),
																		findall(Distance,member([_,Distance,_,_],Set),Distances),
																		findall(Activity,member([_,_,Activity,_],Set),Activities),
																		findall(City,member([_,_,_,City],Set),Cities).
																		



