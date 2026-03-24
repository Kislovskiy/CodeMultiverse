def kUniqueCharacters(strParam):
  k = int(strParam[0])
  inStr = strParam[1:]
  max_substring = ""
  seen = {}
  for i in range(0, len(inStr)):
    tmp = inStr[i]
    seen = {inStr[i]}
    for j in range(i + 1, len(inStr)):
      seen.add(inStr[j])
      if len(seen) <= k:
        tmp = tmp + inStr[j]
      if len(max_substring) < len(tmp):
        max_substring = tmp

  return max_substring

def kUniqueCharacters2(strParam):
  k = int(strParam[0])
  s = strParam[1:]
  i = 0
  j = 0
  max_substring = ""
  while j < len(s):
    tmp = s[i:j]
    if len(set(tmp)) <= k:
      j += 1
    else:
      tmp = s[i:j-1]
      i += 1

    if len(max_substring) < len(tmp):
      max_substring = tmp
      
  return max_substring
  
print(kUniqueCharacters("2aabbacbaa"))
print(kUniqueCharacters("2aabbcbbbadef"))
print(kUniqueCharacters("3aabacbebebe"))
print(kUniqueCharacters("1aabb"))
print(kUniqueCharacters("4aaffaacccerrfffaacca"))
print(kUniqueCharacters("2aabbaaccbbaaccaabb"))

print(f"kUniqueCharacters2={kUniqueCharacters2("2aabbacbaa")}")
print(f"kUniqueCharacters2={kUniqueCharacters2("2aabbcbbbadef")}")
print(f"kUniqueCharacters2={kUniqueCharacters2("3aabacbebebe")}")
print(f"kUniqueCharacters2={kUniqueCharacters2("1aabb")}")
print(f"kUniqueCharacters2={kUniqueCharacters2("4aaffaacccerrfffaacca")}")
print(f"kUniqueCharacters2={kUniqueCharacters2("2aabbaaccbbaaccaabb")}")